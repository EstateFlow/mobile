package ua.nure.estateflow.ui.signin

import android.credentials.CreateCredentialRequest.Builder
import android.credentials.GetCredentialRequest
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.credentials.CredentialOption
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.nure.estateflow.config.WEB_CLIENT_ID
import ua.nure.estateflow.data.datasource.DataSourceResponse
import ua.nure.estateflow.data.datasource.auth.AuthDataSource
import ua.nure.estateflow.navigation.NestedGraph
import ua.nure.estateflow.navigation.Screen
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
            SignIn.Action.OnLogin -> onLogin(
                login = state.value.login,
                password = state.value.password
            )
            is SignIn.Action.OnNavigate -> {
                _event.emit(SignIn.Event.OnNavigate(destination = action.destination))
            }
            is SignIn.Action.onLoginChanged -> {
                _state.update {
                    it.copy(
                        login = action.login
                    )
                }
            }
            is SignIn.Action.onPasswordChanged -> {
                _state.update {
                    it.copy(
                        password = action.password
                    )
                }
            }
            SignIn.Action.OnGoogleLogin -> {
            }

        }
    }

    fun onLogin(login: String, password: String) {
        viewModelScope.launch {
            authDataSource.signIn(
                login = login,
                password = password
            ).collect {
                when(it) {
                    is DataSourceResponse.Error<*> -> {
                        _state.update {
                            it.copy(inProgress = false)
                        }
                        it.message?.let {
                            _event.emit(SignIn.Event.OnMessage(message = it))
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
                        _event.emit(SignIn.Event.OnNavigate(destination = Screen.Main.List))
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private fun onGoogleLogin() {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(WEB_CLIENT_ID)
            .build()

//        val request: GetCredentialRequest = Builder()
//            .addCredentialOption(googleIdOption)
//            .build()
    }
}