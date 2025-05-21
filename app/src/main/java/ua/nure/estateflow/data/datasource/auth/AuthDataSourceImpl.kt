package ua.nure.estateflow.data.datasource.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ua.nure.estateflow.data.datasource.DataSourceResponse
import ua.nure.estateflow.data.datasource.profile.Profile
import ua.nure.estateflow.data.datasource.profile.ProfileDataSource
import ua.nure.estateflow.data.datasource.token.TokenDataSource
import ua.nure.estateflow.data.datasource.token.TokenDataSourceImpl
import ua.nure.estateflow.data.remote.auth.AuthApi
import ua.nure.estateflow.data.remote.auth.dto.AuthRequest
import ua.nure.estateflow.data.remote.parseError
import kotlin.math.log

class AuthDataSourceImpl(
    private val authApi: AuthApi,
    private val tokenDataSource: TokenDataSource,
    private val profileDataSource: ProfileDataSource,
) : AuthDataSource {
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
                            Profile(
                                login = login
                            )
                        )
                        emit(DataSourceResponse.Success())
                    }
                }
                else -> {
                    emit(parseError(errorBody = errorBody()))
                }
            }
        }
    }

}