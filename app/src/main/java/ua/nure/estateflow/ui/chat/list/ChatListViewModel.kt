package ua.nure.estateflow.ui.chat.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ua.nure.estateflow.data.datasource.ai.AiChatDataSource
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val aiChatDataSource: AiChatDataSource,
) : ViewModel() {
    private val _event = MutableSharedFlow<ChatList.Event>()
    val event = _event.asSharedFlow()
    private val _state = MutableStateFlow<ChatList.State>(ChatList.State())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            aiChatDataSource.get().collect {  }
        }

    }

    fun onAction(action: ChatList.Action) = viewModelScope.launch {
        when(action) {
            is ChatList.Action.OnNavigate -> {
                _event.emit(ChatList.Event.OnNavigate(destination = action.destination))
            }

            ChatList.Action.OnBack -> {
                _event.emit(ChatList.Event.OnBack)
            }
        }
    }
}