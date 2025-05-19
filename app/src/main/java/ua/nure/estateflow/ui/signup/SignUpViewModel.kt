package ua.nure.estateflow.ui.signup

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
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authDataSource: AuthDataSource
) : ViewModel() {
    private val _event = MutableSharedFlow<SignUp.Event>()
    val event = _event.asSharedFlow()
    private val _state = MutableStateFlow(SignUp.State())
    val state = _state.asStateFlow()

    fun onAction(action: SignUp.Action) = viewModelScope.launch {
        when(action) {
            is SignUp.Action.OnNavigate -> {
                _event.emit(SignUp.Event.OnNavigate(action.destination))
            }
            SignUp.Action.OnRegister -> register(
                name = state.value.name,
                login = state.value.login,
                password = state.value.password,
            )
            is SignUp.Action.onNameChanged -> {
                _state.update {
                    it.copy(
                        name = action.name
                    )
                }
            }
            is SignUp.Action.onPasswordChanged -> {
                _state.update {
                    it.copy(
                        password = action.password
                    )
                }
            }
            is SignUp.Action.onLoginChanged -> {
                _state.update {
                    it.copy(
                        login = action.login
                    )
                }
            }
            is SignUp.Action.onConfirmPasswordChanged -> {
                _state.update {
                    it.copy(
                        confirmPassword = action.password
                    )
                }
            }
        }
    }

    fun register(name: String, login: String, password: String) {
        viewModelScope.launch {
            authDataSource.signUp(
                name = name,
                login = login,
                password = password
            ).collect {
                when (it) {
                    is DataSourceResponse.Error<*> -> {
                        _state.update {
                            it.copy(inProgress = false)
                        }
                        it.message?.let {
                            _event.emit(SignUp.Event.OnMessage(message = it))
                        }
                    }
                    DataSourceResponse.InProgress -> {
                        _state.update {
                            it.copy(inProgress = true)
                        }
                    }
                    is DataSourceResponse.Success<*> -> {
                        (it.payload as? String)?.let {
                            _event.emit(SignUp.Event.OnMessage(it))
                        }
                        _event.emit(SignUp.Event.OnNavigate(destination = Screen.SignIn))
                        _state.update {
                            it.copy(inProgress = false)
                        }
                    }
                }
            }
        }
    }
}