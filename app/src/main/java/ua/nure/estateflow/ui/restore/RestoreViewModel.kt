package ua.nure.estateflow.ui.restore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.nure.estateflow.data.datasource.DataSourceResponse
import ua.nure.estateflow.data.datasource.auth.AuthDataSource
import ua.nure.estateflow.navigation.Screen
import ua.nure.estateflow.ui.restore.Restore.Event.*
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class RestoreViewModel @Inject constructor(
    private val authDataSource: AuthDataSource,
) : ViewModel(){
    private val _event = MutableSharedFlow<Restore.Event>()
    val event = _event.asSharedFlow()
    private val _state = MutableStateFlow(Restore.State())
    val state = _state.asStateFlow()

    fun onAction(action: Restore.Action) = viewModelScope.launch {
        when(action) {
            is Restore.Action.OnNavigate -> {
                _event.emit(OnNavigate(destination = action.destination))
            }

            Restore.Action.OnBack -> {
                _event.emit(Restore.Event.OnBack)
            }
            is Restore.Action.OnLoginChanged -> {
                _state.update {
                    it.copy(login = action.login)
                }
            }
            Restore.Action.OnRestorePassword -> restorePassword(state.value.login)
        }
    }

    private fun restorePassword(login: String) = viewModelScope.launch {
        authDataSource.restorePassword(login = login).collect {
            when(it) {
                is DataSourceResponse.Error<*> -> {
                    _state.update {
                        it.copy(inProgress = false)
                    }
                    it.message?.let {
                        _event.emit(Restore.Event.OnMessage(message = it))
                    }
                }
                DataSourceResponse.InProgress -> {
                    _state.update {
                        it.copy(inProgress = true)
                    }
                }
                is DataSourceResponse.Success<*> -> {
                    _state.update {
                        it.copy(inProgress = false)
                    }
                    (it.payload as? String)?.let {
                        _event.emit(Restore.Event.OnMessage(message = it))
                    }
                    _event.emit(Restore.Event.OnNavigate(destination = Screen.Auth.SignIn))
                }
            }
        }

    }
}