package ua.nure.estateflow.ui.ai

import ua.nure.estateflow.data.local.entity.MessageEntity
import ua.nure.estateflow.navigation.Screen

object Chat {
    sealed interface Event {
        data class OnMessage(val message: String) : Event
        data class OnNavigate(val destination: Screen): Event
        data object OnBack : Event
    }

    sealed interface Action {
        data class OnNavigate(val destination: Screen) : Action
        data object OnBack : Action
        data class OnMessage(val message: String) : Action
        data object OnSend : Action
    }

    data class State (
        val inProgress: Boolean = false,
        val message: String = "",
        val messages: List<MessageEntity> = emptyList<MessageEntity>()
    )
}