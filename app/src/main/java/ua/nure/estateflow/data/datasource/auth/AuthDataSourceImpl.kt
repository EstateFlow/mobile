package ua.nure.estateflow.data.datasource.auth

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ua.nure.estateflow.data.datasource.DataSourceResponse
import ua.nure.estateflow.data.datasource.profile.Profile
import ua.nure.estateflow.data.datasource.profile.ProfileDataSource
import ua.nure.estateflow.data.datasource.token.TokenDataSource
import ua.nure.estateflow.data.datasource.token.TokenDataSourceImpl
import ua.nure.estateflow.data.remote.auth.AuthApi
import ua.nure.estateflow.data.remote.auth.dto.AuthRequest
import ua.nure.estateflow.data.remote.auth.dto.ResetPasswordRequest
import ua.nure.estateflow.data.remote.parseError
import java.io.InterruptedIOException
import kotlin.math.log

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