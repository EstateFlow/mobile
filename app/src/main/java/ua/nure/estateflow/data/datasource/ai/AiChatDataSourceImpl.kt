package ua.nure.estateflow.data.datasource.ai

import kotlinx.coroutines.CloseableCoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ua.nure.estateflow.data.datasource.DataSourceResponse
import ua.nure.estateflow.data.datasource.db.DbDataSource
import ua.nure.estateflow.data.remote.ai.AiChatApi
import ua.nure.estateflow.di.DbDeliveryDispatcher
import javax.sql.DataSource

class AiChatDataSourceImpl @OptIn(ExperimentalCoroutinesApi::class) constructor(
    private val iaChatApi: AiChatApi,
    private val dbDataSource: DbDataSource,
    @DbDeliveryDispatcher private val dbDeliveryDispatcher: CloseableCoroutineDispatcher,

) : AiChatDataSource {
    override fun get(): Flow<List<Any>> = flow {
        iaChatApi.loadConversations()
    }
}