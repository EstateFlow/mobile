package ua.nure.estateflow.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NestedGraph {
    @Serializable data object Main : NestedGraph()
    @Serializable data object Chat : NestedGraph()
    @Serializable data object Profile: NestedGraph()
}