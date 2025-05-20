package ua.nure.estateflow.data.datasource.property

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ua.nure.estateflow.data.datasource.DataSourceResponse
import ua.nure.estateflow.data.remote.parseError
import ua.nure.estateflow.data.remote.property.PropertyApi

class PropertyDataSourceImpl(
    private val propertyApi: PropertyApi,
) : PropertyDataSource{
    private val TAG by lazy { PropertyDataSourceImpl::class.simpleName}
    override suspend fun getProperties(): Flow<DataSourceResponse<Any>> = flow {
        propertyApi.load().run {
            when {
                isSuccessful -> {
                    body()?.let {
                        Log.d(TAG, "getProperties: $it")
                    }
                }
                else -> {
//                    emit(DataSourceResponse.Error(message = parseError(errorBody = errorBody())))
                }
            }
        }
    }

}