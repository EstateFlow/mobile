package ua.nure.estateflow.ui.main.details

import ua.nure.estateflow.data.local.entity.Property
import ua.nure.estateflow.navigation.Screen

object MainDetails {
    sealed interface Event {
        data class OnMessage(val message: String) : Event
        data class OnNavigate(val destination: Screen): Event
        data object OnBack : Event
    }

    sealed interface Action {
        data class OnNavigate(val destination: Screen) : Action
        data object OnBack : Action
    }

    data class State (
        val inProgress: Boolean = false,
        val property: Property? = null
    )
}