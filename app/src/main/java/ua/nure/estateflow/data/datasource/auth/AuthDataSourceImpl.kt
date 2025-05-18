package ua.nure.estateflow.data.datasource.auth

import kotlinx.coroutines.flow.Flow
import ua.nure.estateflow.data.datasource.DataSourceResponse

class AuthDataSourceImpl : AuthDataSource {
    override suspend fun signUp(
        name: String,
        login: String,
        password: String
    ): Flow<DataSourceResponse<Any>> {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(login: String, password: String): Flow<DataSourceResponse<Any>> {
        TODO("Not yet implemented")
    }

}