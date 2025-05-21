package ua.nure.estateflow.ui.main.list

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ua.nure.estateflow.ui.components.EFFilter
import ua.nure.estateflow.ui.components.Item
import ua.nure.estateflow.ui.signup.SignUp


@Composable
fun MainListScreen(
    viewModel: MainListViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsStateWithLifecycle()
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
        onAction = viewModel::onAction
    )
}

@Composable
private fun MainListScreenContent(
    state: State<MainList.State>,
    onAction: (MainList.Action) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 32.dp,
                start = 16.dp,
                end = 16.dp
            )
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
//        EFFilter(
//            modifier = Modifier
//                .fillMaxWidth(),
//            imageURL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSjEw6t-ZsbPUfj6aBY3OFYudP16ZQcJX2k4Q&s",
//            onAIClick = {
//
//            }
//        )

        LazyColumn(
            modifier = Modifier
                .weight(1F),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = state.value.properties,
                key = { it.propertyEntity.id }
            ) { item ->
                Item(
                    modifier = Modifier,
                    imageURL = item.images.first {
                        it.isPrimary
                    }.imageUrl,
//                    imageURL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSjEw6t-ZsbPUfj6aBY3OFYudP16ZQcJX2k4Q&s",
                    price = item.propertyEntity.price,
                    currency = item.propertyEntity.currency,
                    size = item.propertyEntity.size,
                    rooms = item.propertyEntity.rooms,
                    address = item.propertyEntity.address,
                    onItemClick = {}
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