package ua.nure.estateflow.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ua.nure.estateflow.ui.main.details.MainDetailsScreen
import ua.nure.estateflow.ui.main.list.MainListScreen

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation<NestedGraph.Main> (
        startDestination = Screen.Main.List
    ) {
        composable<Screen.Main.List> {
            MainListScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
        composable<Screen.Main.Details> {
            MainDetailsScreen(
                mainDetailsViewModel = hiltViewModel(),
                navController = navController
            )
        }
    }
}