package ua.nure.estateflow.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable data object  SignUp : Screen()
    @Serializable data object  SignIn : Screen()
    @Serializable data object  RestorePassword : Screen()

    @Serializable sealed class Main : Screen() {
        @Serializable data object List : Main()
        @Serializable data object Details : Main()
        @Serializable data object Gallery : Main()
    }
}