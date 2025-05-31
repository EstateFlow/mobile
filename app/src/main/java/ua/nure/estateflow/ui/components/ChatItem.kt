package ua.nure.estateflow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.nure.estateflow.data.remote.ai.dto.Sender
import ua.nure.estateflow.navigation.Screen
import ua.nure.estateflow.ui.ai.MessagePart
import ua.nure.estateflow.ui.theme.AppTheme

@Composable
fun ChatItem(
    modifier: Modifier = Modifier,
    parts: List<MessagePart>,
    sender: Sender,
    onNavigate: (Screen) -> Unit
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = if (sender == Sender.user) AppTheme.dimension.LargeSpace else 0.dp,
                end = if (sender == Sender.ai) AppTheme.dimension.LargeSpace else 0.dp,
            )
            .background(
                color = if (sender == Sender.user) AppTheme.color.background else AppTheme.color.backgroundAi,
                shape = RoundedCornerShape(
                    topStart = AppTheme.dimension.ChatRadius,
                    topEnd = AppTheme.dimension.ChatRadius,
                    bottomStart = if (sender == Sender.ai) 0.dp else AppTheme.dimension.ChatRadius,
                    bottomEnd = if (sender == Sender.user) 0.dp else AppTheme.dimension.ChatRadius,
                )
            )
            .padding(vertical = AppTheme.dimension.NormalSpace, horizontal = AppTheme.dimension.NormalSpace)
        ,
    ) {
        parts.forEach {
            val mod = if(it.id == null) {
                Modifier
            } else {
                Modifier
                    .clickable {
                        onNavigate(Screen.Main.Details(id = it.id))
                    }
            }
            Text(
                modifier = mod,
                text = it.text,
                style = if(it.id == null) {
                    AppTheme.typography.regularTextStyle
                } else {
                    AppTheme.typography.regularTextStyle.copy(
                        color = AppTheme.color.focusedTextColor
                    )
                },
            )

        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ChatItemPreview(modifier: Modifier = Modifier) {
//    Column {
//        ChatItem(modifier, text = "hello this is a message from chat jhhjsdfhjksdfsdfioers", sender = Sender.ai, )
//        ChatItem(modifier, text = "hello this is a message from chat", sender = Sender.user)
//    }
}