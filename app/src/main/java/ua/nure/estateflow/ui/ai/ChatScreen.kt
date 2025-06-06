package ua.nure.estateflow.ui.ai

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ua.nure.estateflow.R
import ua.nure.estateflow.ui.components.ChatItem
import ua.nure.estateflow.ui.components.EFTitlebar
import ua.nure.estateflow.ui.theme.AppTheme

@Composable
fun ChatListScreen(
    viewModel: ChatViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    LaunchedEffect(key1 = Unit) {
        viewModel.event.collect {
            when(it) {
                is Chat.Event.OnMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                is Chat.Event.OnNavigate -> {
                    navController.navigate(it.destination)
                }

                Chat.Event.OnBack -> {
                    navController.navigateUp()
                }

                is Chat.Event.OnScrollToLast -> {
                    listState.animateScrollToItem(it.index)
                }
            }
        }
    }
    ChatScreenContent(
        state = state.value,
        listState = listState,
        onAction = viewModel::onAction
    )

}

@Composable
private fun ChatScreenContent(
    state: Chat.State,
    listState: LazyListState,
    onAction: (Chat.Action) -> Unit
) {
    var message by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.color.appBackground),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        EFTitlebar(
            title = stringResource(R.string.chatListScreen),
            isBackEnabled = true,
            onBack = {
                onAction(Chat.Action.OnBack)
            }
        )
        LazyColumn(
            modifier = Modifier
                .weight(1F)
                .padding(
                    bottom = AppTheme.dimension.SmallSpace,
                    start = AppTheme.dimension.SmallSpace,
                    end = AppTheme.dimension.SmallSpace
                ),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimension.SmallSpace),
        ) {
            items(items = state.messages, key = {it.message.id}) {
                ChatItem(
                    modifier = Modifier,
                    parts = it.parts,
                    sender = it.message.sender,
                    onNavigate = {
                        onAction(Chat.Action.OnNavigate(destination = it))
                    }
                )
            }

        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimension.SmallSpace)
            ,
            value = message,
            colors = TextFieldDefaults.colors()
                .copy(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedTextColor = AppTheme.color.focusedTextColor,
                    unfocusedTextColor = AppTheme.color.labelTextColor,
                    unfocusedLabelColor = AppTheme.color.focusedTextColor,

                    ),
            onValueChange = {
                message = it
                onAction(Chat.Action.OnMessage(it))

            },
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            onAction(Chat.Action.OnSend)
                            message = ""
                        },
                    painter = painterResource(R.drawable.play),
                    contentDescription = ""
                )
            }
        )
        Spacer(
            modifier = Modifier
                .padding(
                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding(),
                )
        )
    }

}

@Preview(showSystemUi = true)
@Composable
private fun ChatScreenPreview (modifier: Modifier = Modifier) {
    ChatScreenContent(
        state = Chat.State(),
        listState = rememberLazyListState()
    ) { }
}