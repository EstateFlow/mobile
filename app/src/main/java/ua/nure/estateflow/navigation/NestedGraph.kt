package ua.nure.estateflow.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NestedGraph {
    @Serializable data object Main : NestedGraph()
}