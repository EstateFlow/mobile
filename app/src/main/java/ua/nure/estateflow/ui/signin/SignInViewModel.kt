package ua.nure.estateflow.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ua.nure.estateflow.data.datasource.auth.AuthDataSource
import ua.nure.estateflow.ui.signup.SignUp
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authDataSource: AuthDataSource
) : ViewModel() {
    private val _event = MutableSharedFlow<SignIn.Event>()
    val event = _event.asSharedFlow()
    private val _state = MutableStateFlow(SignIn.State())
    val state = _state.asStateFlow()

    fun onAction(action: SignIn.Action) = viewModelScope.launch {
        when(action) {
            SignIn.Action.OnLogin -> TODO()
            is SignIn.Action.OnNavigate -> TODO()
            is SignIn.Action.onLoginChanged -> TODO()
            is SignIn.Action.onPasswordChanged -> TODO()
            SignIn.Action.OnGoogleLogin -> TODO()
        }
    }
}