package ua.nure.estateflow.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ua.nure.estateflow.data.datasource.auth.AuthDataSource
import javax.inject.Inject

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
            is SignUp.Action.OnNavigate -> TODO()
            SignUp.Action.OnRegister -> TODO()
            is SignUp.Action.onNameChanged -> TODO()
            is SignUp.Action.onPasswordChanged -> TODO()
        }
    }

    fun register(name: String, login: String, password: String) {

    }
}