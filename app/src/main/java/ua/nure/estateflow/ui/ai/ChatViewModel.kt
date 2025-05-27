package ua.nure.estateflow.ui.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import ua.nure.estateflow.data.datasource.DataSourceResponse
import ua.nure.estateflow.data.datasource.ai.AiChatDataSource
import ua.nure.estateflow.ui.ai.Chat.Event.*
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val aiChatDataSource: AiChatDataSource,
) : ViewModel() {
    private val _event = MutableSharedFlow<Chat.Event>()
    val event = _event.asSharedFlow()
    private val _state = MutableStateFlow<Chat.State>(Chat.State())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            aiChatDataSource.get().collect { list ->
                _state.update {
                    it.copy(
                        messages = list
                    )
                }
                delay(300)
                _event.emit(Chat.Event.OnScrollToLast(index = list.size))
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