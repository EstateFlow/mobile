package ua.nure.estateflow.ui.profile.show

import ua.nure.estateflow.data.local.entity.Property
import ua.nure.estateflow.data.remote.auth.dto.Role
import ua.nure.estateflow.navigation.Screen

object Profile {
    sealed interface Event {
        data class OnMessage(val message: String) : Event
        data class OnNavigate(val destination: Screen) : Event
        data object OnBack : Event
    }

    sealed interface Action {
        data class OnNavigate(val destination: Screen) : Action
        data object OnBack : Action
        data object OnEdit : Action
    }

    data class State(
        val inProgress: Boolean = false,
        val username: String = "",
        val email: String = "",
        val role: Role? = null,
        val avatarUrl: String = "",
        val isEMailVerified: Boolean? = null,
        val bio: String = "",
        val properties: List<Property> = emptyList<Property>()
    )
}