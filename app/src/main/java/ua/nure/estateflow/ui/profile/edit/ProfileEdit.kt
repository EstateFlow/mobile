package ua.nure.estateflow.ui.profile.edit

import ua.nure.estateflow.data.local.entity.Property
import ua.nure.estateflow.data.remote.auth.dto.Role
import ua.nure.estateflow.navigation.Screen

object ProfileEdit {
    sealed interface Event {
        data class OnMessage(val message: String) : Event
        data class OnNavigate(val destination: Screen) : Event
        data object OnBack : Event
    }

    sealed interface Action {
        data class OnNavigate(val destination: Screen) : Action
        data object OnBack : Action
        data class OnUsernameChange(val username: String) : Action
        data class OnAvatarChange(val url: String) : Action
        data class OnBioChange(val bio: String) : Action
        data object OnSave : Action
    }

    data class State(
        val inProgress: Boolean = false,
        val username: String = "",
        val email: String = "",
        val role: Role? = null,
        val avatarUrl: String = "",
        val isEMailVerified: Boolean? = null,
        val bio: String = "",
    )
}