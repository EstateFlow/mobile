package ua.nure.estateflow.ui.main.list

import ua.nure.estateflow.navigation.Screen

object MainList {
    sealed interface Event {
        data class OnMessage(val message: String) : Event
        data class OnNavigate(val destination: Screen): Event
    }

    sealed interface Action {
        data class OnNavigate(val destination: Screen): Action
    }

    data class State (
        val inProgress: Boolean = false,
    )
}