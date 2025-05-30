package ua.nure.estateflow.ui.profile.edit

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
import ua.nure.estateflow.data.datasource.profile.ProfileDataSource
import javax.inject.Inject
import ua.nure.estateflow.ui.profile.edit.ProfileEdit.Event
import ua.nure.estateflow.ui.profile.edit.ProfileEdit.State
import ua.nure.estateflow.ui.profile.edit.ProfileEdit.Action

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val profileDataSource: ProfileDataSource,
    private val authDataSource: AuthDataSource,
) : ViewModel() {
    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()
    private val _state = MutableStateFlow<State>(State())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            profileDataSource.profileFlow.collect { prof ->
                _state.update {
                    it.copy(
                        username = prof?.username ?: "",
                        email = prof?.login ?: "",
                        role = prof?.role,
                        avatarUrl = prof?.avatarUrl ?: "",
                        bio = prof?.bio ?: "",
                    )
                }
            }
        }
    }

    fun onAction(action: Action) = viewModelScope.launch {
        when(action) {
            Action.OnBack -> _event.emit(Event.OnBack)
            is Action.OnNavigate -> _event.emit(Event.OnNavigate(destination = action.destination))
            is Action.OnAvatarChange -> {
                _state.update {
                    it.copy(
                        avatarUrl = action.url
                    )
                }
            }
            is Action.OnBioChange -> {
                _state.update {
                    it.copy(
                        bio = action.bio
                    )
                }
            }
            is Action.OnUsernameChange -> {
                _state.update {
                    it.copy(
                        username = action.username
                    )
                }
            }

            Action.OnSave -> save()
        }
    }

    private fun save() = viewModelScope.launch {
        authDataSource.updateUser(
            avatarUrl = state.value.avatarUrl,
            username = state.value.username,
            bio = state.value.bio
        ).collect {
            when(it) {
                is DataSourceResponse.Error<*> -> {
                    _state.update {
                        it.copy(inProgress = false)
                    }
                    it.message?.let {
                        _event.emit(Event.OnMessage(message = it))
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
                    _event.emit(Event.OnBack)
                }
            }
        }

    }

}