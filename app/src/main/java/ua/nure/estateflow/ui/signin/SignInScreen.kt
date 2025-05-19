package ua.nure.estateflow.ui.signin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun SignInScreen(
    viewModel: SignInViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        viewModel.event.collect {
            when(it) {
                is SignIn.Event.OnMessage -> Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                is SignIn.Event.OnNavigate -> navController.navigate(it.destination)
            }
        }
    }
    SignInScreenContent(
        state =state,
        onAction = viewModel::onAction
    )
}

@Composable
fun SignInScreenContent(
    state: State<SignIn.State>,
    onAction: (SignIn.Action) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF227AC4))
            .padding(top = 32.dp, start = 64.dp, end = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "EstateFlow login screen")
    }
}

@Preview(showSystemUi = true)
@Composable
fun SignInScreenPreview(modifier: Modifier = Modifier) {
    SignInScreenContent(
        state = remember {
            mutableStateOf(
                SignIn.State()
            )
        }
    ) { }

}