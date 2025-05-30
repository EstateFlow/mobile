package ua.nure.estateflow.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ua.nure.estateflow.ui.profile.edit.ProfileEditScreen
import ua.nure.estateflow.ui.profile.show.ProfileScreen

fun NavGraphBuilder.profileGraph(navController: NavController) {
    navigation<NestedGraph.Profile>(
        startDestination = Screen.Profile.ProfileScreen
    ) {
        composable<Screen.Profile.ProfileScreen> {
            ProfileScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }

        composable<Screen.Profile.ProfileEdit> {
            ProfileEditScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
    }


}