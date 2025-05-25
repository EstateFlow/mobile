package ua.nure.estateflow.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ua.nure.estateflow.ui.profile.ProfileScreen
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
        startDestination = Screen.SignIn
    ) {
        composable<Screen.SignUp> {
            SignUpScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
        composable<Screen.SignIn> {
            SignInScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
        composable<Screen.Profile> {
            ProfileScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }

        mainGraph(navController = navController)

    }
}