package ua.nure.estateflow.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import ua.nure.estateflow.R
import ua.nure.estateflow.ui.theme.ToolBarColor

@Composable
fun EFTitlebar(
    modifier: Modifier = Modifier,
    imageURL: String,
    isBackEnabled: Boolean = false,
    title: String,
    onSearch: () -> Unit,
    onAI: () -> Unit,
    onProfile: () -> Unit,

) {
    val context = LocalContext.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(ToolBarColor)
            .padding(top = 32.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        if (isBackEnabled) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = ""
            )
        } else {
            Box(
                modifier = Modifier
                    .size(24.dp)
            )
        }
        Text(
            modifier = Modifier.weight(1F),
            text = title
        )

        Text(
            modifier = Modifier
                .clickable {
                    onProfile()
                },
            text = "John Doe \u2605"
        )
        AsyncImage(
            modifier = Modifier
                .size(33.dp)
                .clip(CircleShape)
                .clickable{
                    onProfile()
                },
            model = ImageRequest.Builder(context)
                .data(imageURL)
                .build(),
            contentDescription = ""
        )
        Image(
            modifier = Modifier
                .clickable {
                    onSearch()
                },
            painter = painterResource(R.drawable.search),
            contentDescription = ""
        )
        Image(
            modifier = Modifier
                .clickable {
                    onAI()
                },
            painter = painterResource(R.drawable.ai),
            contentDescription = ""
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EFTitlebarPreview(modifier: Modifier = Modifier) {
    EFTitlebar(
        modifier,
        imageURL = "",
        isBackEnabled = true,
        title = "Estate Flow",
        onSearch = {},
        onAI = {},
        onProfile = {}
    )
}