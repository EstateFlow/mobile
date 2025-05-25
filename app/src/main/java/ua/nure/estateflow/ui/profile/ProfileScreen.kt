package ua.nure.estateflow.ui.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ua.nure.estateflow.R
import ua.nure.estateflow.ui.components.EFTitlebar
import ua.nure.estateflow.ui.theme.regularTextStyle

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
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        EFTitlebar(
            modifier = Modifier,
            isBackEnabled = true,
            title = stringResource(R.string.profile),
            onBack = {
                onAction(Profile.Action.OnBack)
            }
        )
        Text(
            text = state.username,
            style = regularTextStyle.copy(
                color = Color.Black
            )
        )
        Text(
            text = state.email,
            style = regularTextStyle.copy(
                color = Color.Black
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreenContent(
        state = Profile.State()
    ) {}
}