package ua.nure.estateflow.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable sealed class Auth : Screen() {
        @Serializable data object  SignUp : Auth()
        @Serializable data object  SignIn : Auth()
        @Serializable data object  RestorePassword : Auth()
        @Serializable data object Profile : Auth()
    }

    @Serializable sealed class Main : Screen() {
        @Serializable data object List : Main()
        @Serializable data class Details(val id: String) : Main()
        @Serializable data object Gallery : Main()
    }

    @Serializable sealed class Chat : Screen() {
        @Serializable data object List : Chat()
        @Serializable data class Conversation(val id: String) : Chat()
    }
}