package ua.nure.estateflow.data.datasource.auth

import kotlinx.coroutines.flow.Flow
import ua.nure.estateflow.data.datasource.DataSourceResponse

interface AuthDataSource {
    suspend fun signUp(name: String,login: String, password: String): Flow<DataSourceResponse<Any>>
    suspend fun signIn(login: String, password: String): Flow<DataSourceResponse<Any>>
    suspend fun signInGoogle(idToken: String, email: String): Flow<DataSourceResponse<Any>>
    suspend fun restorePassword(login: String): Flow<DataSourceResponse<Any>>
    suspend fun updateUser(avatarUrl: String, username: String, bio: String): Flow<DataSourceResponse<Any>>
}