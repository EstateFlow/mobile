package ua.nure.estateflow.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import ua.nure.estateflow.R
import ua.nure.estateflow.ui.theme.Background
import ua.nure.estateflow.ui.theme.ToolBarColor

@Composable
fun EFFilter(
    modifier: Modifier = Modifier,
    imageURL: String,
    onAIClick:() -> Unit = {}
) {
    val context = LocalContext.current
    var isExpanded by remember() {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
            .background(ToolBarColor),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            EFButton(
                modifier = modifier,
                label = R.string.askAI,
                backgroundColor = Background,
                textColor = Color.White,
                onClick = {
                    onAIClick()
                }
            )
            Spacer(modifier = Modifier.weight(1F))
            Text(
                text = "John Doe \u2605"
            )
            AsyncImage(
                modifier = Modifier
                    .size(33.dp)
                    .clip(CircleShape),
                model = ImageRequest.Builder(context)
                    .data(imageURL)
                    .build(),
                contentDescription = ""
            )
        }
        AnimatedVisibility(visible = isExpanded) {

        }
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .clickable{
                    isExpanded = !isExpanded
                },
            painter = painterResource(if (isExpanded) R.drawable.collapse else R.drawable.expand),
            contentDescription = ""
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun EFFilterPreview(modifier: Modifier = Modifier) {
    EFFilter(
        modifier = modifier,
        imageURL = ""
    )
}