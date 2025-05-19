package ua.nure.estateflow.ui.signup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ua.nure.estateflow.R
import ua.nure.estateflow.ui.components.EFButton
import ua.nure.estateflow.ui.components.EFTextField
import ua.nure.estateflow.ui.components.OutlinedPasswordTextField
import ua.nure.estateflow.ui.theme.ButtonColor
import ua.nure.estateflow.ui.theme.ButtonTextColor
import ua.nure.estateflow.ui.theme.FocusedTextColor
import ua.nure.estateflow.ui.theme.LabelTextColor
import kotlin.math.log

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        viewModel.event.collect {
            when (it) {
                is SignUp.Event.OnMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }

                is SignUp.Event.OnNavigate -> {
                    navController.navigate(it.destination)
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
    state: State<SignUp.State>,
    onAction: (SignUp.Action) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF227AC4))
            .padding(top = 32.dp, start = 64.dp, end = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        var name by remember { mutableStateOf(state.value.name) }
        var login by remember { mutableStateOf(state.value.login) }
        var password by remember { mutableStateOf(state.value.password) }
        var confirmPassword by remember { mutableStateOf(state.value.confirmPassword) }

        Image(
            modifier = Modifier
                .size(
                    width = 203.dp,
                    height = 244.dp
                ),
            painter = painterResource(R.drawable.estate_flow),
            contentDescription = ""
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )
        EFTextField(
            modifier = Modifier
                .fillMaxWidth(),
            isPassword = false,
            value = name,
            label = R.string.name,
            onValueChange = {
                onAction(SignUp.Action.onNameChanged(it))
            }
        )

        EFTextField(
            modifier = Modifier
                .fillMaxWidth(),
            isPassword = false,
            value = login,
            label = R.string.login,
            onValueChange = {
                onAction(SignUp.Action.onLoginChanged(it))
            }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )

        EFTextField(
            modifier = Modifier
                .fillMaxWidth(),
            isPassword = true,
            value = password,
            label = R.string.password,
            onValueChange = {
                onAction(SignUp.Action.onPasswordChanged(it))
            }
        )
        EFTextField(
            modifier = Modifier
                .fillMaxWidth(),
            isPassword = true,
            value = confirmPassword,
            label = R.string.confirmPassword,
            onValueChange = {
                onAction(SignUp.Action.onConfirmPasswordChanged(it)) 
            }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )
        EFButton(
            modifier = Modifier,
            label = R.string.getStarted,
            onClick = {
                onAction(SignUp.Action.OnRegister)
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun SignUpScreenPreview(modifier: Modifier = Modifier) {
    val state = remember {
        mutableStateOf(
            SignUp.State()
        )
    }
    SignUpScreenContent(
        state = state,
        onAction = {}
    )
}