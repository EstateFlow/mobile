package ua.nure.estateflow.ui.signup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ua.nure.estateflow.R

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        viewModel.event.collect {
            when(it) {
                is SignUp.Event.OnMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                is SignUp.Event.OnNavigate -> {
//                    navController.navigate(it.destination)
                }
            }
        }
    }

    SignUpScreenContent(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun SignUpScreenContent(
    state : State<SignUp.State>,
    onAction: (SignUp.Action) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF227AC4))
            .padding(top = 32.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        var name by remember { mutableStateOf(state.value.name) }
        var login by remember { mutableStateOf(state.value.login) }
        var password by remember { mutableStateOf(state.value.password) }

        Image(
            modifier = Modifier
                .size(
                    width = 203.dp,
                    height = 244.dp
                ),
            painter = painterResource(R.drawable.estate_flow),
            contentDescription = ""
        )
    }
}

@Preview (showSystemUi = true)
@Composable
fun SignUpScreenPreview(modifier: Modifier = Modifier) {
    val state = remember {
        mutableStateOf(
            SignUp.State(
            )
        )
    }
    SignUpScreenContent(
        state = state,
        onAction = {}
    )
}