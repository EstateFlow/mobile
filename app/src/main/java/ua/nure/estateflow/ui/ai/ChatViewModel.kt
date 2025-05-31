package ua.nure.estateflow.ui.ai

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.nure.estateflow.data.datasource.DataSourceResponse
import ua.nure.estateflow.data.datasource.ai.AiChatDataSource
import ua.nure.estateflow.data.local.entity.MessageEntity
import ua.nure.estateflow.data.remote.ai.dto.Sender
import ua.nure.estateflow.ui.ai.Chat.Event.*
import java.net.URL
import javax.inject.Inject
import androidx.core.net.toUri

@OptIn(FlowPreview::class)
@HiltViewModel
class ChatViewModel @Inject constructor(
    private val aiChatDataSource: AiChatDataSource,
) : ViewModel() {
    private val TAG by lazy { ChatViewModel::class.simpleName }

    private val _event = MutableSharedFlow<Chat.Event>()
    val event = _event.asSharedFlow()
    private val _state = MutableStateFlow<Chat.State>(Chat.State())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            aiChatDataSource.get().collect { list ->
                _state.update {
                    it.copy(
                        messages = list.map {
                            MessageHolder(
                                message = it,
                                parts = rebuildListMessage(it.content)
                            )
                        }
                    )
                }
                viewModelScope.launch {
                    if(list.isNotEmpty()) {
                        delay(500)
                        _event.emit(Chat.Event.OnScrollToLast(index = list.size -1))
                    }
                }
            }
        }

        viewModelScope.launch {
            aiChatDataSource.loadHistory().collect {
                when(it) {
                    is DataSourceResponse.Error<*> -> {
                        it.message?.let {
                            _event.emit(Chat.Event.OnMessage(message = it))
                        }
                        _state.update {
                            it.copy(
                                inProgress = false
                            )
                        }
                    }
                    DataSourceResponse.InProgress -> {
                        _state.update {
                            it.copy(
                                inProgress = true
                            )
                        }
                    }
                    is DataSourceResponse.Success<*> -> {
                        _state.update {
                            it.copy(
                                inProgress = false
                            )
                        }
                    }
                }
            }
        }

    }

    private fun rebuildListMessage(message: String): List<MessagePart> {
        val regex = Regex("""\[(.+?)\]\((.+?)\)""")
        val resultParts = mutableListOf<String>()
        val links = mutableListOf<Pair<String, String>>()
        var lastIndex = 0
        for (match in regex.findAll(message)) {
            val range = match.range

            if (range.first > lastIndex) {
                resultParts.add(message.substring(lastIndex, range.first))
            }

            links.add(match.groupValues[1] to match.groupValues[2])
            lastIndex = range.last + 1
        }

        if (lastIndex < message.length) {
            resultParts.add(message.substring(lastIndex))
        }


        return mutableListOf<MessagePart>().apply {
            addAll(
                when {
                    links.isEmpty() -> {
                        resultParts.map {
                            MessagePart(text = it)
                        }
                    }
                    resultParts.isNotEmpty() -> {
                        mutableListOf<MessagePart>().apply {
                            links.zip(resultParts).map { (link, result) ->
                                add(MessagePart(text = result))
                                add(MessagePart(text = link.first, id = link.second.toUri().getQueryParameter("propertyId")))
                            }
                            if(resultParts.size > links.size) {
                                add(
                                    MessagePart(
                                        text = resultParts.get(resultParts.size -1)
                                    )
                                )
                            }
                        }
                    }
                    else ->{
                        listOf<MessagePart>(

                        )
                    }
                }
            )

        }


    }

    fun onAction(action: Chat.Action) = viewModelScope.launch {
        when(action) {
            is Chat.Action.OnNavigate -> {
                _event.emit(OnNavigate(destination = action.destination))
            }

            Chat.Action.OnBack -> {
                _event.emit(Chat.Event.OnBack)
            }

            is Chat.Action.OnMessage -> {
                _state.update {
                    it.copy(
                        message = action.message
                    )
                }
            }
            Chat.Action.OnSend -> {
                if (state.value.message.isNotEmpty()) {
                    send(message = state.value.message)
                }
            }
        }
    }

    private fun send(message: String) = viewModelScope.launch {
        aiChatDataSource.send(message = message).collect {
            when(it) {
                is DataSourceResponse.Error<*> -> {
                    _state.update {
                        it.copy(
                            inProgress = false
                        )
                    }
                    it.message?.let {
                        _event.emit(Chat.Event.OnMessage(it))
                    }
                }
                DataSourceResponse.InProgress -> {
                    _state.update {
                        it.copy(
                            inProgress = true
                        )
                    }
                }
                is DataSourceResponse.Success<*> -> {
                    _state.update {
                        it.copy(
                            inProgress = false
                        )
                    }
                }
            }
        }
    }
}

data class MessagePart(
    val text: String,
    val id: String? = null
)

data class MessageHolder(
    val message: MessageEntity,
    val parts: List<MessagePart> = emptyList<MessagePart>()
)