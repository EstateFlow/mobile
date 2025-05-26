package ua.nure.estateflow.navigation

import androidx.compose.material3.Text
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ua.nure.estateflow.ui.ai.ChatListScreen

fun NavGraphBuilder.chatGraph(navController: NavController) {
    navigation<NestedGraph.Chat>(
        startDestination = Screen.Chat.List
    ) {
        composable<Screen.Chat.List> {
            ChatListScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
        composable<Screen.Chat.Conversation> {
            Text(
                text = "Chat conversation"
            )
        }


    }
}