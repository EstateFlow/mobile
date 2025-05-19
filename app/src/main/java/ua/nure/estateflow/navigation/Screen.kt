package ua.nure.estateflow.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable data object  SignUp : Screen()
    @Serializable data object  SignIn : Screen()
    @Serializable data object  RestorePassword : Screen()
}