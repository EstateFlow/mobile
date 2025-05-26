package ua.nure.estateflow.ui.chat.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(

) : ViewModel() {
    private val _event = MutableSharedFlow<ChatList.Event>()
    val event = _event.asSharedFlow()
    private val _state = MutableStateFlow<ChatList.State>(ChatList.State())
    val state = _state.asStateFlow()

    init {

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