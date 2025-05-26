package ua.nure.estateflow.data.datasource.property

import android.util.Log
import kotlinx.coroutines.CloseableCoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ua.nure.estateflow.data.datasource.DataSourceResponse
import ua.nure.estateflow.data.datasource.db.DbDataSource
import ua.nure.estateflow.data.local.entity.Property
import ua.nure.estateflow.data.remote.parseError
import ua.nure.estateflow.data.remote.property.PropertyApi
import ua.nure.estateflow.data.remote.property.dto.toEntity
import ua.nure.estateflow.di.DbDeliveryDispatcher

class PropertyDataSourceImpl @OptIn(ExperimentalCoroutinesApi::class) constructor(
    private val propertyApi: PropertyApi,
    private val dbDataSource: DbDataSource,
    @DbDeliveryDispatcher private val dbDeliveryDispatcher: CloseableCoroutineDispatcher,
) : PropertyDataSource{
    private val TAG by lazy { PropertyDataSourceImpl::class.simpleName}

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun get(): Flow<List<Property>> =
        dbDataSource.dbFlow
            .flatMapLatest { db -> db.propertyDao.getAll() }
            .flowOn(dbDeliveryDispatcher)
            .catch { it.printStackTrace() }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getById(id: String): Flow<Property> =
        dbDataSource.dbFlow
            .flatMapLatest { db -> db.propertyDao.getById(id = id) }
            .flowOn(dbDeliveryDispatcher)
            .catch { it.printStackTrace() }

    override suspend fun load(): Flow<DataSourceResponse<List<Property>>> = flow {
        emit(DataSourceResponse.InProgress)
        propertyApi.load().run {
            when {
                isSuccessful -> {
                    body()?.let { properties ->
                        dbDataSource.db.propertyDao.insert(
                            properties.map {
                                it.toEntity()
                            }
                        )

                        dbDataSource.db.imageDao.insert(
                            properties.flatMap { it.images }.map { it.toEntity() }
                        )

                        dbDataSource.db.viewDao.insert(
                            properties.flatMap { it.views }.map { it.toEntity() }
                        )
                        dbDataSource.db.ownerDao.insert(
                            properties.map { it.owner.toEntity() }
                        )
                    }

                }
                else -> {
                    parseError(errorBody = errorBody())
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getWishlist(): Flow<List<Property>> =
        dbDataSource.dbFlow
            .flatMapLatest { db -> db.propertyDao.getWishlist() }
            .flowOn(dbDeliveryDispatcher)
            .catch { it.printStackTrace() }


}