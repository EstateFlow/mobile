package ua.nure.estateflow.data.datasource.auth

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ua.nure.estateflow.data.datasource.DataSourceResponse
import ua.nure.estateflow.data.datasource.profile.Profile
import ua.nure.estateflow.data.datasource.profile.ProfileDataSource
import ua.nure.estateflow.data.datasource.token.TokenDataSource
import ua.nure.estateflow.data.remote.auth.AuthApi
import ua.nure.estateflow.data.remote.auth.dto.AuthRequest
import ua.nure.estateflow.data.remote.auth.dto.GoogleAuthRequest
import ua.nure.estateflow.data.remote.auth.dto.ResetPasswordRequest
import ua.nure.estateflow.data.remote.auth.dto.UpdateUserRequest
import ua.nure.estateflow.data.remote.parseError
import java.io.InterruptedIOException

class AuthDataSourceImpl(
    private val authApi: AuthApi,
    private val tokenDataSource: TokenDataSource,
    private val profileDataSource: ProfileDataSource,
) : AuthDataSource {
    private val TAG by lazy { AuthDataSourceImpl::class.simpleName }
    override suspend fun signUp(
        name: String,
        login: String,
        password: String
    ): Flow<DataSourceResponse<Any>> = flow {
        emit(DataSourceResponse.InProgress)
        authApi.signUp(
            body = AuthRequest(
                name = name,
                login = login,
                password = password
            )
        ).run {
            when {
                isSuccessful -> {
                    emit(
                        DataSourceResponse.Success(
                            payload = body()?.message
                        )
                    )
                }
                else -> {
                    emit(parseError(errorBody = errorBody()))
                }
            }
        }
    }

    override suspend fun signIn(login: String, password: String): Flow<DataSourceResponse<Any>> = flow {
        emit(DataSourceResponse.InProgress)
        try {
            authApi.signIn(
                body = AuthRequest(
                    login = login,
                    password = password
                )
            ).run {
                when {
                    isSuccessful -> {
                        body()?.let {
                            tokenDataSource.setToken(it.accessToken)
                            profileDataSource.setProfile(
                                profile = Profile(
                                    login = login
                                )
                            )
                            loadUser()
                            emit(DataSourceResponse.Success())
                        }
                    }
                    else -> {
                        emit(parseError(errorBody = errorBody()))
                    }
                }
            }
        }
        catch (ex: InterruptedIOException) {
            emit(DataSourceResponse.Error<String>(message = "Server is down"))
        }
    }

    override suspend fun signInGoogle(idToken: String, email: String): Flow<DataSourceResponse<Any>> = flow {
        emit(DataSourceResponse.InProgress)
        try {
            authApi.signIn(
                body = AuthRequest(
                    login = email,
                    password = "Secret1"
                )
            ).run {
                when {
                    isSuccessful -> {
                        body()?.let {
                            tokenDataSource.setToken(it.accessToken)
                            profileDataSource.setProfile(
                                profile = Profile(
                                    login = email
                                )
                            )
                            loadUser()
                            emit(DataSourceResponse.Success())
                        }
                    }
                    else -> {
                        emit(parseError(errorBody = errorBody()))
                    }
                }
            }
        }
        catch (ex: InterruptedIOException) {
            emit(DataSourceResponse.Error<String>(message = "Server is down"))
        }
    }

    override suspend fun restorePassword(login: String): Flow<DataSourceResponse<Any>> = flow {
        emit(DataSourceResponse.InProgress)
        authApi.resetPassword(body = ResetPasswordRequest(email = login)).run {
            when {
                isSuccessful -> {
                    emit(DataSourceResponse.Success(body()?.message))
                }
                else -> {
                    parseError(errorBody = errorBody())
                }
            }
        }
    }

    override suspend fun updateUser(
        avatarUrl: String,
        username: String,
        bio: String
    ): Flow<DataSourceResponse<Any>> = flow {
        emit(DataSourceResponse.InProgress)
        authApi.updateUser(
            body = UpdateUserRequest(
                username = username,
                avatarUrl = avatarUrl,
                bio = bio
            )
        ).run {
            when {
                isSuccessful -> {
                    body()?.let { dto ->
                        profileDataSource.profile?.let { p ->
                            profileDataSource.setProfile(
                                p.copy(
                                    username = dto.username,
                                    avatarUrl = dto.avatarUrl ?: "",
                                    bio = dto.bio ?: ""
                                )
                            )
                        }

                    }
                    emit(DataSourceResponse.Success())
                }
                else -> {
                    parseError(errorBody = errorBody())
                }
            }
        }
    }

    private fun loadUser() {
        CoroutineScope(Dispatchers.IO).launch {
            authApi.loadUser().run {
                when {
                    isSuccessful -> {
                        body()?.let {
                            profileDataSource.setProfile(
                                profile = Profile(
                                    login = it.email,
                                    username = it.username,
                                    role = it.role,
                                    isEmailVerified = it.isEmailVerified,
                                    avatarUrl = it.avatarUrl,
                                    bio = it.bio
                                )
                            )
                        }
                    }
                }
            }
        }
    }

}