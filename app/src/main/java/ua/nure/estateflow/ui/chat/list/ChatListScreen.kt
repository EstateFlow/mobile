package ua.nure.estateflow.ui.chat.list

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ua.nure.estateflow.R
import ua.nure.estateflow.ui.components.EFTitlebar

@Composable
fun ChatListScreen(
    viewModel: ChatListViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.event.collect {
            when(it) {
                is ChatList.Event.OnMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                is ChatList.Event.OnNavigate -> {
                    navController.navigate(it.destination)
                }

                ChatList.Event.OnBack -> {
                    navController.popBackStack()
                }
            }
        }
    }
    ChatListScreenContent(
        state = state.value,
        onAction = viewModel::onAction
    )

}

@Composable
private fun ChatListScreenContent(
    state: ChatList.State,
    onAction: (ChatList.Action) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        EFTitlebar(
            title = stringResource(R.string.chatListScreen),
            isBackEnabled = true,
            onBack = {
                onAction(ChatList.Action.OnBack)
            }
        )

    }

}

@Preview(showSystemUi = true)
@Composable
private fun ChatListScreenPreview (modifier: Modifier = Modifier) {
    ChatListScreenContent(
        state = ChatList.State()
    ) { }
}