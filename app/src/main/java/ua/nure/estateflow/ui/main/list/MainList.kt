package ua.nure.estateflow.ui.main.list

import ua.nure.estateflow.data.datasource.profile.Profile
import ua.nure.estateflow.data.local.entity.Property
import ua.nure.estateflow.navigation.Screen

object MainList {
    sealed interface Event {
        data class OnMessage(val message: String) : Event
        data class OnNavigate(val destination: Screen): Event
    }

    sealed interface Action {
        data class OnNavigate(val destination: Screen) : Action
        data class OnSearch(val search: String) : Action
        data class OnRoomsFrom(val rooms: Int?) : Action
        data class OnRoomsTo(val rooms: Int?) : Action
        data class OnPriceFrom(val price: Int?) : Action
        data class OnPriceTo(val price: Int?) : Action
        data class OnAreaFrom(val area: Int?) : Action
        data class OnAreaTo(val area: Int?) : Action
        data class OnForRent(val checked: Boolean) : Action
        data class OnForPurchase(val checked: Boolean) : Action
        data class OnHose(val checked: Boolean) : Action
        data class OnApartment(val checked: Boolean) : Action
    }

    data class State (
        val inProgress: Boolean = false,
        val properties: List<Property> = emptyList<Property>(),
        val profile: Profile? = null
    )
}