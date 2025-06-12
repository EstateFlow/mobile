package ua.nure.estateflow.ui.main.details

import android.content.Intent
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
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import ua.nure.estateflow.R
import ua.nure.estateflow.data.remote.property.dto.PropertyType
import ua.nure.estateflow.data.remote.property.dto.TransactionType
import ua.nure.estateflow.ui.components.EFTitlebar
import ua.nure.estateflow.ui.components.EfGalleryIndicator
import ua.nure.estateflow.ui.theme.AppTheme

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
            .background(color = AppTheme.color.appBackground)
    ) {
        val galleryState = rememberLazyListState()
        val context = LocalContext.current
        val shareMessage = stringResource(R.string.shareMessage)

        val target = LatLng(50.45, 30.52)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(target, 12F)
        }

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
                active = R.drawable.active_circle,
                passive = R.drawable.passive_circle,
            )

        }

        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .weight(1F)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = AppTheme.dimension.SmallSpace),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    modifier = Modifier
                        .padding(
                            top = AppTheme.dimension.NormalSpace,
                            start = AppTheme.dimension.SmallSpace
                        ),
                    text = stringResource(R.string.detais),
                    style = AppTheme.typography.largeTextStyle
                )
                Spacer(
                    modifier = Modifier.weight(1F)
                )
                Icon(
                    modifier = Modifier
                        .padding(horizontal = AppTheme.dimension.SmallSpace)
                        .size(AppTheme.dimension.IconSize)
                        .clip(CircleShape)
                        .clickable {
                            onAction(MainDetails.Action.OnAddToWishList)
                        },
                    painter = painterResource(R.drawable.heart),
                    tint = if(state.property?.propertyEntity?.isWished == true) Color.Red else AppTheme.color.controlBackground,
                    contentDescription = ""
                )
                Icon(
                    modifier = Modifier
                        .padding(horizontal = AppTheme.dimension.SmallSpace)
                        .size(AppTheme.dimension.IconSize)
                        .clickable {
                            context.startActivity(
                                Intent.createChooser(
                                    Intent(
                                        Intent.ACTION_SEND
                                    ).apply {
                                        type = "text/plain"
                                        putExtra(
                                            Intent.EXTRA_TEXT,
                                            "\u2302 ${state.property?.propertyEntity?.title ?: ""}\n\u2316 ${state.property?.propertyEntity?.address ?: ""}\n\u2606 ${state.property?.owner?.username ?: ""} \n\u2709 ${state.property?.owner?.email ?: ""}"
                                        )
                                    },
                                    shareMessage
                                )
                            )
                        }
                    ,
                    painter = painterResource(R.drawable.share),
                    contentDescription = "",
                    tint = AppTheme.color.controlBackground
                )

            }

            state.property?.let { prop ->
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = AppTheme.dimension.SmallSpace,
                            vertical = AppTheme.dimension.SmallSpace
                        ),
                    text = String.format(
                        stringResource(R.string.priceFormat),
                        prop.propertyEntity.price,
                        prop.propertyEntity.currency
                    ),
                    style = AppTheme.typography.largeTextStyle.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = AppTheme.dimension.SmallSpace,
                            vertical = AppTheme.dimension.SmallSpace
                        ),
                    text = when(prop.propertyEntity.propertyType) {
                        PropertyType.house -> stringResource(R.string.house)
                        PropertyType.apartment -> stringResource(R.string.apartment)
                    },
                    style = AppTheme.typography.regularTextStyle
                )
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = AppTheme.dimension.SmallSpace,
                            vertical = AppTheme.dimension.SmallSpace
                        ),
                    text = when(prop.propertyEntity.transactionType) {
                        TransactionType.rent -> stringResource(R.string.rent)
                        TransactionType.sale -> stringResource(R.string.sale)
                    },
                    style = AppTheme.typography.regularTextStyle
                )
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = AppTheme.dimension.SmallSpace,
                            vertical = AppTheme.dimension.SmallSpace
                        ),
                    text = prop.propertyEntity.address,
                    style = AppTheme.typography.regularTextStyle
                )
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = AppTheme.dimension.SmallSpace,
                            vertical = AppTheme.dimension.SmallSpace
                        ),
                    text = prop.propertyEntity.size + " m\u00B2",
                    style = AppTheme.typography.regularTextStyle
                )
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = AppTheme.dimension.SmallSpace,
                            vertical = AppTheme.dimension.SmallSpace
                        ),
                    text = context.resources.getQuantityString(
                        R.plurals.roomsPostfix,
                        state.property.propertyEntity.rooms,
                        state.property.propertyEntity.rooms
                    ),
                    style = AppTheme.typography.regularTextStyle
                )
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = AppTheme.dimension.SmallSpace,
                            vertical = AppTheme.dimension.SmallSpace
                        ),
                    text = prop.propertyEntity.description,
                    style = AppTheme.typography.regularTextStyle
                )
                Text(
                    modifier = Modifier
                        .padding(
                            top = AppTheme.dimension.NormalSpace,
                            start = AppTheme.dimension.SmallSpace
                        ),
                    text = stringResource(R.string.contact),
                    style = AppTheme.typography.regularTextStyle
                )
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = AppTheme.dimension.SmallSpace,
                            vertical = AppTheme.dimension.SmallSpace
                        ),
                    text = prop.owner.username,
                    style = AppTheme.typography.regularTextStyle
                )

                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = AppTheme.dimension.SmallSpace,
                            vertical = AppTheme.dimension.SmallSpace
                        ),
                    text = prop.owner.email,
                    style = AppTheme.typography.regularTextStyle
                )
                Text(
                    modifier = Modifier
                        .padding(
                            top = AppTheme.dimension.NormalSpace,
                            start = AppTheme.dimension.SmallSpace
                        ),
                    text = stringResource(R.string.location),
                    style = AppTheme.typography.regularTextStyle
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ){
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState
                    ) {
                        Marker (
                            state = MarkerState(position = target),
                            title = state.property.propertyEntity.title
                        )
                    }
                }

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