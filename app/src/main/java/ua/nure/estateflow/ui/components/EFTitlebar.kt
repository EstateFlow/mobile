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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import ua.nure.estateflow.R
import ua.nure.estateflow.ui.theme.AppTheme

@Composable
fun EFTitlebar(
    modifier: Modifier = Modifier,
    imageURL: String = "",
    isProfileEnabled: Boolean = false,
    isAIEnabled: Boolean = false,
    isSearchEnabled: Boolean = false,
    isBackEnabled: Boolean = false,
    isEditEnabled: Boolean = false,
    title: String,
    username: String = "",
    onBack: () -> Unit = {},
    onSearch: () -> Unit = {},
    onAI: () -> Unit = {},
    onProfile: () -> Unit = {},
    onEdit: () -> Unit = {},
) {
    val context = LocalContext.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(AppTheme.color.toolBarColor)
            .padding(top = 32.dp, bottom = 8.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        if (isBackEnabled) {
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        onBack()
                    },
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "",
                tint = AppTheme.color.controlBackground
            )
        } else {
            Box(
                modifier = Modifier
                    .size(24.dp)
            )
        }
        Text(
            modifier = Modifier.weight(1F)
                .padding(start = AppTheme.dimension.SmallSpace),
            text = title,
            style = AppTheme.typography.regularTextStyle
        )

        if (isProfileEnabled) {
            Text(
                modifier = Modifier
                    .padding(end = AppTheme.dimension.SmallSpace)
                    .clickable {
                        onProfile()
                    },
                text = username,
                style = AppTheme.typography.regularTextStyle
            )
            AsyncImage(
                modifier = Modifier
                    .size(AppTheme.dimension.IconSize)
                    .clip(CircleShape)
                    .clickable{
                        onProfile()
                    },
                model = ImageRequest.Builder(context)
                    .data(imageURL)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
        }

        if (isSearchEnabled) {
            Icon(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .clickable {
                        onSearch()
                    },
                painter = painterResource(R.drawable.search),
                contentDescription = "",
                tint = AppTheme.color.controlBackground
            )
        }

        if (isAIEnabled) {
            Icon(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .clickable {
                        onAI()
                    },
                painter = painterResource(R.drawable.ai),
                contentDescription = "",
                tint = AppTheme.color.controlBackground
            )
        }
        if (isEditEnabled) {
            Icon(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .clickable {
                        onEdit()
                    },
                painter = painterResource(R.drawable.edit),
                contentDescription = "",
                tint = AppTheme.color.controlBackground
            )
        }
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