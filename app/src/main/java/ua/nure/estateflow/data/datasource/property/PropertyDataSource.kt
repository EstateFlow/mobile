package ua.nure.estateflow.data.datasource.property

import kotlinx.coroutines.flow.Flow
import ua.nure.estateflow.data.datasource.DataSourceResponse

interface PropertyDataSource {
    suspend fun getProperties(): Flow<DataSourceResponse<Any>>
}