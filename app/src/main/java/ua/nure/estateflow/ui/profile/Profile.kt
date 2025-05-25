package ua.nure.estateflow.ui.profile

import ua.nure.estateflow.BuildConfig
import ua.nure.estateflow.navigation.Screen

object Profile {
    sealed interface Event {
        data class OnMessage(val message: String) : Event
        data class OnNavigate(val destination: Screen) : Event
    }

    sealed interface Action {
        data class OnNavigate(val destination: Screen) : Action
        data object OnBack : Action
    }

    data class State(
        val inProgress: Boolean = false,
    )
}