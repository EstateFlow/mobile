package ua.nure.estateflow.ui.chat.list

import ua.nure.estateflow.navigation.Screen

object ChatList {
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
    )
}