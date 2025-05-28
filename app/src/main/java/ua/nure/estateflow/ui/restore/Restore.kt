package ua.nure.estateflow.ui.restore

import ua.nure.estateflow.BuildConfig
import ua.nure.estateflow.navigation.Screen

object Restore {
    sealed interface Event {
        data class OnMessage(val message: String) : Event
        data class OnNavigate(val destination: Screen): Event
        data object OnBack : Event
    }

    sealed interface Action {
        data class OnNavigate(val destination: Screen): Action
        data object OnBack : Action
        data class OnLoginChanged(val login: String) : Action
        data object OnRestorePassword : Action
    }

    data class State(
        val login: String = if (BuildConfig.DEBUG) "John.Doe@gmail.com" else "",
        val inProgress: Boolean = false,
    )
}