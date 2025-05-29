package ua.nure.estateflow.ui.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import ua.nure.estateflow.R
import ua.nure.estateflow.navigation.Screen
import ua.nure.estateflow.ui.components.EFTitlebar
import ua.nure.estateflow.ui.components.Item
import ua.nure.estateflow.ui.main.list.MainList
import ua.nure.estateflow.ui.theme.AppTheme

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        viewModel.event.collect {
            when(it) {
                Profile.Event.OnBack -> {
                    navController.popBackStack()
                }
                is Profile.Event.OnMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                is Profile.Event.OnNavigate -> {
                    navController.navigate(it.destination)
                }
            }
        }
    }
    ProfileScreenContent(
        state = state.value,
        onAction = viewModel::onAction
    )
}

@Composable
private fun ProfileScreenContent(
    state: Profile.State,
    onAction: (Profile.Action) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.color.appBackground)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val context = LocalContext.current
        EFTitlebar(
            modifier = Modifier,
            isBackEnabled = true,
            title = stringResource(R.string.profile),
            onBack = {
                onAction(Profile.Action.OnBack)
            }
        )
        if(state.avatarUrl.isNotEmpty()) {
            AsyncImage(
                modifier = Modifier
                    .padding(top = AppTheme.dimension.SmallSpace)
                    .size(100.dp)
                    .clip(CircleShape)
                ,
                model = ImageRequest.Builder(context)
                    .data(state.avatarUrl)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimension.NormalSpace)
                .padding(horizontal = AppTheme.dimension.NormalSpace),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.person),
                contentDescription = "",
                tint = AppTheme.color.controlBackground
            )
            Text(
                modifier = Modifier
                    .padding(start = AppTheme.dimension.SmallSpace),
                text = state.username,
                style = AppTheme.typography.regularTextStyle
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimension.NormalSpace)
                .padding(horizontal = AppTheme.dimension.NormalSpace),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.mail),
                contentDescription = "",
                tint = AppTheme.color.controlBackground
            )
            Text(
                modifier = Modifier
                    .padding(start = AppTheme.dimension.SmallSpace),
                text = state.email,
                style = AppTheme.typography.regularTextStyle
            )
        }


        Text(
            modifier = Modifier
                .padding(
                    top = AppTheme.dimension.LargeSpace,
                    start = AppTheme.dimension.NormalSpace
                )
                .fillMaxWidth()
            ,
            text = stringResource(R.string.wishlist),
            style = AppTheme.typography.largeTextStyle.copy(
                textAlign = TextAlign.Start
            )
        )
        if(state.properties.isEmpty()) {
            Text(
                modifier = Modifier
                    .padding(top = AppTheme.dimension.NormalSpace)
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimension.NormalSpace)
                ,
                text = stringResource(R.string.emptyWishlistMessage),
                style = AppTheme.typography.regularTextStyle
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(
                        start = AppTheme.dimension.NormalSpace,
                        top = AppTheme.dimension.SmallSpace,
                        bottom = AppTheme.dimension.SmallSpace,
                        end = AppTheme.dimension.NormalSpace
                    )
                    .weight(1F),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimension.NormalSpace)
            ) {
                items(
                    items = state.properties,
                    key = { it.propertyEntity.id }
                ) { item ->
                    Item(
                        modifier = Modifier,
                        imageURL = item.images.firstOrNull() {
                            it.isPrimary
                        }?.imageUrl ?: item.images.firstOrNull()?.imageUrl ?: "",
                        price = item.propertyEntity.price,
                        currency = item.propertyEntity.currency,
                        size = item.propertyEntity.size,
                        rooms = item.propertyEntity.rooms,
                        address = item.propertyEntity.address,
                        onItemClick = {
                            onAction(Profile.Action.OnNavigate(Screen.Main.Details(id = item.propertyEntity.id)))
                        }
                    )
                }

                item {
                    Spacer(
                        modifier = Modifier.height(70.dp)
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreenContent(
        state = Profile.State()
    ) {}
}