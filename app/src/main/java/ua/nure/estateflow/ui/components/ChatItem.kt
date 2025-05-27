package ua.nure.estateflow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.nure.estateflow.data.remote.ai.dto.Sender
import ua.nure.estateflow.ui.theme.Background
import ua.nure.estateflow.ui.theme.BackgroundAi
import ua.nure.estateflow.ui.theme.appDimensions
import ua.nure.estateflow.ui.theme.regularTextStyle

@Composable
fun ChatItem(
    modifier: Modifier = Modifier,
    text: String,
    sender: Sender
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = if (sender == Sender.user) MaterialTheme.appDimensions.LargeSpace else 0.dp,
                end = if (sender == Sender.ai) MaterialTheme.appDimensions.LargeSpace else 0.dp,
            )
            .background(color = if (sender == Sender.user) Background else BackgroundAi, shape = RoundedCornerShape(
            topStart = MaterialTheme.appDimensions.ChatRadius,
            topEnd = MaterialTheme.appDimensions.ChatRadius,
            bottomStart = if (sender == Sender.ai) 0.dp else MaterialTheme.appDimensions.ChatRadius,
            bottomEnd = if (sender == Sender.user) 0.dp else MaterialTheme.appDimensions.ChatRadius,
        )),
    ) {
        Text(
            modifier = Modifier
                .padding(all = MaterialTheme.appDimensions.NormalSpace),
            text = text,
            style = regularTextStyle,
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ChatItemPreview(modifier: Modifier = Modifier) {
    Column {
        ChatItem(modifier, text = "hello this is a message from chat jhhjsdfhjksdfsdfioers", sender = Sender.ai)
        ChatItem(modifier, text = "hello this is a message from chat", sender = Sender.user)
    }
}