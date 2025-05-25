package ua.nure.estateflow.data.datasource.wishList

import kotlinx.coroutines.CloseableCoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import ua.nure.estateflow.data.datasource.DataSourceResponse
import ua.nure.estateflow.data.datasource.db.DbDataSource
import ua.nure.estateflow.data.remote.wishlist.WishListApi
import ua.nure.estateflow.data.remote.wishlist.dto.WishListDto
import ua.nure.estateflow.di.DbDeliveryDispatcher
import javax.inject.Inject

class WishListDataSourceImpl @OptIn(ExperimentalCoroutinesApi::class) constructor(
    private val wishListApi: WishListApi,
    private val dbDataSource: DbDataSource,
    @DbDeliveryDispatcher private val dbDeliveryDispatcher: CloseableCoroutineDispatcher,
): WishListDataSource {
    override suspend fun add(propertyId: String): Flow<DataSourceResponse<Nothing>> = flow {
        emit(DataSourceResponse.InProgress)
            wishListApi.add(body = WishListDto(
                propertyId = propertyId
            )).run {
                when {
                    isSuccessful -> {
                        dbDataSource.db.propertyDao.update(
                            property = dbDataSource.db.propertyDao.getByIdStraight(id = propertyId).copy(
                                isWished = true
                            )
                        )
                        emit(DataSourceResponse.Success())
                    }
                    else -> {
                        emit(DataSourceResponse.Error<Nothing>(message = null))
                    }
                }
            }
    }

    override suspend fun delete(propertyId: String): Flow<DataSourceResponse<Nothing>> = flow {
        emit(DataSourceResponse.InProgress)
            wishListApi.delete(
                propertyId = propertyId
            ).run {
                when {
                    isSuccessful -> {
                        dbDataSource.db.propertyDao.update(
                            property = dbDataSource.db.propertyDao.getByIdStraight(id = propertyId).copy(
                                isWished = false
                            )
                        )
                        emit(DataSourceResponse.Success())
                    }
                    else -> {
                        emit(DataSourceResponse.Error<Nothing>(message = null))
                    }
                }
        }
    }
}