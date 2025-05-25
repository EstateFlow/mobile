package ua.nure.estateflow.ui.main.details

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import ua.nure.estateflow.R
import ua.nure.estateflow.data.remote.property.dto.PropertyType
import ua.nure.estateflow.data.remote.property.dto.TransactionType
import ua.nure.estateflow.ui.components.EFTitlebar
import ua.nure.estateflow.ui.components.EfGalleryIndicator
import ua.nure.estateflow.ui.theme.appDimensions
import ua.nure.estateflow.ui.theme.largeTextStyle
import ua.nure.estateflow.ui.theme.regularTextStyle

@Composable
fun MainDetailsScreen(
    mainDetailsViewModel: MainDetailsViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val state = mainDetailsViewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit) {
        mainDetailsViewModel.event.collect {
            when(it) {
                is MainDetails.Event.OnMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                is MainDetails.Event.OnNavigate -> {
                    navController.navigate(it.destination)
                }

                MainDetails.Event.OnBack -> {
                    navController.popBackStack()
                }
            }
        }
    }
    MainDetailsScreenContent(
        state = state.value,
        onAction = mainDetailsViewModel::onAction
    )
}

@Composable
private fun MainDetailsScreenContent(
    state: MainDetails.State,
    onAction: (MainDetails.Action) -> Unit
) {
    Column(
        modifier = Modifier
    ) {
        val galleryState = rememberLazyListState()
        EFTitlebar(
            isBackEnabled = true,
            title = stringResource(R.string.mainDetailsScreen),
            onBack = {
                onAction(MainDetails.Action.OnBack)
            }
        )
        state.property?.images?.let { images ->
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(top = MaterialTheme.appDimensions.SmallSpace)
                ,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                state = galleryState,
                flingBehavior = rememberSnapFlingBehavior(lazyListState = galleryState)
            ) {
                items(
                    items = images,
                    key = { it.id }
                ) { image ->
                    AsyncImage(
                        modifier = Modifier
                            .fillParentMaxWidth()
//                            .clip(RoundedCornerShape(MaterialTheme.appDimensions.Radius))
                        ,
                        model = image.imageUrl,
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds
                    )
                }
            }

            EfGalleryIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                selected = galleryState.firstVisibleItemIndex,
                total = images.size,
                active = R.drawable.heart,
                passive = R.drawable.ai,
            )

        }

        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .weight(1F)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(end = MaterialTheme.appDimensions.SmallSpace),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    modifier = Modifier
                        .padding(
                            top = MaterialTheme.appDimensions.NormalSpace,
                            start = MaterialTheme.appDimensions.SmallSpace
                        ),
                    text = stringResource(R.string.detais),
                    style = largeTextStyle.copy(
                        color = Color.Black
                    )
                )
                Spacer(
                    modifier = Modifier.weight(1F)
                )
                Icon(
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.appDimensions.SmallSpace)
                        .size(MaterialTheme.appDimensions.IconSize)
                        .clip(CircleShape)
                        .clickable {
                            onAction(MainDetails.Action.OnAddToWishList)
                        },
                    painter = painterResource(R.drawable.heart),
                    tint = if(state.property?.propertyEntity?.isWished == true) Color.Red else Color.Black,
                    contentDescription = ""
                )
                Icon(
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.appDimensions.SmallSpace)
                        .size(MaterialTheme.appDimensions.IconSize),
                    painter = painterResource(R.drawable.share),
                    contentDescription = ""
                )

            }

            state.property?.let { prop ->
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = MaterialTheme.appDimensions.SmallSpace,
                            vertical = MaterialTheme.appDimensions.SmallSpace
                        ),
                    text = String.format(
                        stringResource(R.string.priceFormat),
                        prop.propertyEntity.price,
                        prop.propertyEntity.currency
                    ),
                    style = largeTextStyle.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = MaterialTheme.appDimensions.SmallSpace,
                            vertical = MaterialTheme.appDimensions.SmallSpace
                        ),
                    text = when(prop.propertyEntity.propertyType) {
                        PropertyType.house -> stringResource(R.string.house)
                        PropertyType.apartment -> stringResource(R.string.apartment)
                    },
                    style = regularTextStyle.copy(
                        color = Color.Black
                    )
                )
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = MaterialTheme.appDimensions.SmallSpace,
                            vertical = MaterialTheme.appDimensions.SmallSpace
                        ),
                    text = when(prop.propertyEntity.transactionType) {
                        TransactionType.rent -> stringResource(R.string.rent)
                        TransactionType.sale -> stringResource(R.string.sale)
                    },
                    style = regularTextStyle.copy(
                        color = Color.Black
                    )
                )
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = MaterialTheme.appDimensions.SmallSpace,
                            vertical = MaterialTheme.appDimensions.SmallSpace
                        ),
                    text = prop.propertyEntity.address,
                    style = regularTextStyle.copy(
                        color = Color.Black
                    )
                )
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = MaterialTheme.appDimensions.SmallSpace,
                            vertical = MaterialTheme.appDimensions.SmallSpace
                        ),
                    text = prop.propertyEntity.size + " m\u00B2",
                    style = regularTextStyle.copy(
                        color = Color.Black
                    )
                )
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = MaterialTheme.appDimensions.SmallSpace,
                            vertical = MaterialTheme.appDimensions.SmallSpace
                        ),
                    text = String
                        .format(
                            stringResource(R.string.roomsDetails),
                            prop.propertyEntity.rooms,
                            stringResource(R.string.roomsPostfix)
                        ),
                    style = regularTextStyle.copy(
                        color = Color.Black
                    )
                )
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = MaterialTheme.appDimensions.SmallSpace,
                            vertical = MaterialTheme.appDimensions.SmallSpace
                        ),
                    text = prop.propertyEntity.description,
                    style = regularTextStyle.copy(
                        color = Color.Black
                    )
                )
                Text(
                    modifier = Modifier
                        .padding(
                            top = MaterialTheme.appDimensions.NormalSpace,
                            start = MaterialTheme.appDimensions.SmallSpace
                        ),
                    text = stringResource(R.string.contact),
                    style = largeTextStyle.copy(
                        color = Color.Black
                    )
                )
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = MaterialTheme.appDimensions.SmallSpace,
                            vertical = MaterialTheme.appDimensions.SmallSpace
                        ),
                    text = prop.owner.username,
                    style = regularTextStyle.copy(
                        color = Color.Black
                    )
                )

                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = MaterialTheme.appDimensions.SmallSpace,
                            vertical = MaterialTheme.appDimensions.SmallSpace
                        ),
                    text = prop.owner.email,
                    style = regularTextStyle.copy(
                        color = Color.Black
                    )
                )

                Box(modifier = Modifier.size(50.dp))

            }
        }


    }
}

@Preview(showBackground = true)
@Composable
private fun MainDetailsScreenPreview(modifier: Modifier = Modifier) {
    MainDetailsScreenContent(
        state = MainDetails.State()
    ) { }
}