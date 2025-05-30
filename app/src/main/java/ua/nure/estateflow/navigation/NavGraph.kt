package ua.nure.estateflow.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ua.nure.estateflow.ui.restore.RestoreScreen
import ua.nure.estateflow.ui.signin.SignInScreen
import ua.nure.estateflow.ui.signup.SignUpScreen

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = Screen.Auth.SignIn
    ) {
        composable<Screen.Auth.SignUp> {
            SignUpScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
        composable<Screen.Auth.SignIn> {
            SignInScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }

        composable<Screen.Auth.RestorePassword> {
            RestoreScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }

        mainGraph(navController = navController)
        chatGraph(navController = navController)
        profileGraph(navController = navController)

    }
}