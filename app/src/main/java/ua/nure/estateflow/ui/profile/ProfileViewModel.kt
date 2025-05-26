package ua.nure.estateflow.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.nure.estateflow.data.datasource.profile.ProfileDataSource
import ua.nure.estateflow.data.datasource.property.PropertyDataSource
import ua.nure.estateflow.ui.signin.SignIn
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileDataSource: ProfileDataSource,
    private val propertyDataSource: PropertyDataSource
) : ViewModel() {
    private val _event = MutableSharedFlow<Profile.Event>()
    val event = _event.asSharedFlow()
    private val _state = MutableStateFlow(Profile.State())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            profileDataSource.profileFlow.collect{ profile ->
                _state.update {
                    it.copy(
                        username = profile?.username ?: "",
                        email = profile?.login ?: "",
                        role = profile?.role,
                        isEMailVerified = profile?.isEmailVerified
                    )
                }
            }
        }

        viewModelScope.launch {
            propertyDataSource.getWishlist().collect { list ->
                _state.update {
                    it.copy(
                        properties = list
                    )
                }
            }
        }
    }

    fun onAction(action: Profile.Action) = viewModelScope.launch {
        when(action) {
            Profile.Action.OnBack -> {
                _event.emit(Profile.Event.OnBack)
            }
            is Profile.Action.OnNavigate -> {
                _event.emit(Profile.Event.OnNavigate(destination = action.destination))
            }
        }
    }

}