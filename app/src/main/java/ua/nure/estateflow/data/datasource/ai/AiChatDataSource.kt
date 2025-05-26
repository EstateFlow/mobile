package ua.nure.estateflow.data.datasource.ai

import kotlinx.coroutines.flow.Flow
import ua.nure.estateflow.data.datasource.DataSourceResponse
import ua.nure.estateflow.data.local.entity.MessageEntity

interface AiChatDataSource {
    fun loadHistory(): Flow<DataSourceResponse<Any>>
    fun create(title: String)
    fun send(message: String): Flow<DataSourceResponse<Any>>
    fun get(): Flow<List<MessageEntity>>
}