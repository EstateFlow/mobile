package ua.nure.estateflow.data.datasource.wishList

import kotlinx.coroutines.flow.Flow
import ua.nure.estateflow.data.datasource.DataSourceResponse

interface WishListDataSource {
    suspend fun add(propertyId: String): Flow<DataSourceResponse<Nothing>>
    suspend fun delete(propertyId: String): Flow<DataSourceResponse<Nothing>>
}