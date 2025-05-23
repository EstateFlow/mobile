package ua.nure.estateflow.ui.main.list

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ua.nure.estateflow.ui.components.EFFilter
import ua.nure.estateflow.ui.components.EFTitlebar
import ua.nure.estateflow.ui.components.Item
import ua.nure.estateflow.ui.signup.SignUp
import ua.nure.estateflow.ui.theme.appDimensions

private val TAG = "MainListScreen"

@Composable
fun MainListScreen(
    viewModel: MainListViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsStateWithLifecycle()
    val search = viewModel.search.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.event.collect {
            when (it) {
                is MainList.Event.OnMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }

                is MainList.Event.OnNavigate -> {
                    navController.navigate(it.destination)
                }
            }
        }
    }
    MainListScreenContent(
        state = state,
        search = search.value,
        onAction = viewModel::onAction
    )
}

@Composable
private fun MainListScreenContent(
    state: State<MainList.State>,
    search: String = "",
    onAction: (MainList.Action) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        var isFilterActive by remember {
            mutableStateOf(false)
        }

        EFTitlebar(
            modifier = Modifier,
            imageURL = "https://www.bhg.com/thmb/H9VV9JNnKl-H1faFXnPlQfNprYw=/1799x0/filters:no_upscale():strip_icc()/white-modern-house-curved-patio-archway-c0a4a3b3-aa51b24d14d0464ea15d36e05aa85ac9.jpg",
            isBackEnabled = false,
            title = "Estate Flow",
            onSearch = {
                isFilterActive = !isFilterActive
            },
            onAI = {},
            onProfile = {}
        )

        AnimatedVisibility(visible = isFilterActive) {
            EFFilter(
                modifier = Modifier.padding(MaterialTheme.appDimensions.SmallSpace),
                search = search,
                onSearchChanged = {
                    onAction(MainList.Action.OnSearch(search = it))
                }
            )
        }

        LazyColumn(
            modifier = Modifier
                .padding(
                    start = MaterialTheme.appDimensions.NormalSpace,
                    top = MaterialTheme.appDimensions.SmallSpace,
                    bottom = MaterialTheme.appDimensions.SmallSpace,
                    end = MaterialTheme.appDimensions.NormalSpace
                )
                .weight(1F),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.appDimensions.NormalSpace)
        ) {
            items(
                items = state.value.properties,
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
                    onItemClick = {}
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

@Preview(showSystemUi = true)
@Composable
private fun MainListScreenPreview() {
    MainListScreenContent(
        state = remember {
            mutableStateOf(MainList.State())
        }
    ) { }
}