package ua.nure.estateflow.data.datasource.ai

import kotlinx.coroutines.flow.Flow

interface AiChatDataSource {
    fun get(): Flow<List<Any>>
}