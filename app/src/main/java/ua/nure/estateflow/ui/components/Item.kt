package ua.nure.estateflow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import ua.nure.estateflow.R
import ua.nure.estateflow.ui.theme.Background
import ua.nure.estateflow.ui.theme.DescriptionTextColor

@Composable
fun Item(
    modifier: Modifier = Modifier,
    imageURL: String,
    price: String,
    currency: String,
    size: String,
    rooms: Int,
    address: String,
    onItemClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .height(105.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Background)
            .clickable {
                onItemClick()
            }
    ) {
        val context = LocalContext.current
        AsyncImage(
            modifier = Modifier
                .size(width = 141.dp, height = 105.dp)
                .clip(RoundedCornerShape(10.dp))
            ,
            model = ImageRequest.Builder(context)
                .data(imageURL)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                color = DescriptionTextColor,
                text = "$price $currency"
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = DescriptionTextColor,
                text = address
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                color = DescriptionTextColor,
                text = "$rooms rooms"
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                color = DescriptionTextColor,
                text = "$size m\u00B2"
            )
        }
    }
}

@Preview
@Composable
private fun ItemPreview(modifier: Modifier = Modifier) {
    Item(
        modifier = modifier,
        imageURL = "https://example.com/images/new-house-interior.jpg",
        price = "4000",
        currency = "$",
        size = "234",
        rooms =  1,
        address = "ilsdufh"
    )
}