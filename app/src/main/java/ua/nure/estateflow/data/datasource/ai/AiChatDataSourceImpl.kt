package ua.nure.estateflow.data.datasource.ai

import kotlinx.coroutines.CloseableCoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ua.nure.estateflow.data.datasource.DataSourceResponse
import ua.nure.estateflow.data.datasource.db.DbDataSource
import ua.nure.estateflow.data.local.entity.MessageEntity
import ua.nure.estateflow.data.remote.ai.AiChatApi
import ua.nure.estateflow.data.remote.ai.dto.CreateConversationDto
import ua.nure.estateflow.data.remote.ai.dto.MessageRequest
import ua.nure.estateflow.data.remote.ai.dto.toEntity
import ua.nure.estateflow.data.remote.parseError
import ua.nure.estateflow.di.DbDeliveryDispatcher

class AiChatDataSourceImpl @OptIn(ExperimentalCoroutinesApi::class) constructor(
    private val aiChatApi: AiChatApi,
    private val dbDataSource: DbDataSource,
    @DbDeliveryDispatcher private val dbDeliveryDispatcher: CloseableCoroutineDispatcher,

    ) : AiChatDataSource {
    override fun loadHistory(): Flow<DataSourceResponse<Any>> = flow {
        aiChatApi.loadHistory().run {
            when {
                isSuccessful -> {
                    body()?.let {
                        dbDataSource.db.messageDao.insert(
                            it.historyMessages.messages.map { it.toEntity() }
                        )
                    }
                    emit(DataSourceResponse.Success())
                }
                code() == 404 -> {
                    create(title = "")
                }
                else -> {
                    parseError(errorBody = errorBody())
                }
            }
        }
    }

    override fun create(title: String) {
        CoroutineScope(Dispatchers.IO).launch {
            aiChatApi.createConversation(body = CreateConversationDto(title = title)).run {
                when {
                    isSuccessful -> {
                        loadHistory()
                    }
                    else -> {}
                }
            }
        }
    }

    override fun send(message: String): Flow<DataSourceResponse<Any>> = flow {
        emit(DataSourceResponse.InProgress)
        aiChatApi.sendMessage(body = MessageRequest(message = message)).run {
            when {
                isSuccessful -> {
                    body()?.let {
                        dbDataSource.db.messageDao.insert(it.toEntity())
                    }
                    emit(DataSourceResponse.Success())
                }
                else -> {
                    parseError(errorBody = errorBody())
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun get(): Flow<List<MessageEntity>> =
        dbDataSource.dbFlow
            .flatMapLatest { db -> db.messageDao.getAll() }
            .flowOn(dbDeliveryDispatcher)
            .catch { it.printStackTrace() }
}