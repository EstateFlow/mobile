package ua.nure.estateflow.data.datasource.property

import kotlinx.coroutines.flow.Flow
import ua.nure.estateflow.data.datasource.DataSourceResponse
import ua.nure.estateflow.data.local.entity.Property

interface PropertyDataSource {
    suspend fun get(): Flow<List<Property>>
    suspend fun getById(id: String): Flow<Property>
    suspend fun load(): Flow<DataSourceResponse<List<Property>>>
    suspend fun getWishlist(): Flow<List<Property>>
}