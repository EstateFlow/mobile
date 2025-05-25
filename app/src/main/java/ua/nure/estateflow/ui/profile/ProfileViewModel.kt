package ua.nure.estateflow.ui.profile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import ua.nure.estateflow.data.datasource.profile.ProfileDataSource
import ua.nure.estateflow.ui.signin.SignIn
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileDataSource: ProfileDataSource,
) : ViewModel() {
    private val _event = MutableSharedFlow<Profile.Event>()
    val event = _event.asSharedFlow()
    private val _state = MutableStateFlow(Profile.State())
    val state = _state.asStateFlow()


}