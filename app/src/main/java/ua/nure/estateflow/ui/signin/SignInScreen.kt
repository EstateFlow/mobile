package ua.nure.estateflow.ui.signin

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ua.nure.estateflow.R
import ua.nure.estateflow.navigation.Screen
import ua.nure.estateflow.ui.components.EFButton
import ua.nure.estateflow.ui.components.EFTextField
import ua.nure.estateflow.ui.signup.SignUp
import ua.nure.estateflow.ui.theme.DescriptionTextColor
import ua.nure.estateflow.ui.theme.FocusedTextColor
import ua.nure.estateflow.ui.theme.HelpingTextColor

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
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )
        EFTextField(
            modifier = Modifier
                .fillMaxWidth(),
            isPassword = false,
            value = login,
            label = R.string.login,
            onValueChange = {
                onAction(SignIn.Action.onLoginChanged(it))
            }
        )
        EFTextField(
            modifier = Modifier
                .fillMaxWidth(),
            isPassword = true,
            value = password,
            label = R.string.password,
            onValueChange = {
                onAction(SignIn.Action.onPasswordChanged(it))
            }
        )
        Spacer(
            modifier = Modifier
                .height(8.dp)
        )
        Text(
            modifier = Modifier
                .clickable {
                    onAction(SignIn.Action.OnNavigate(destination = Screen.RestorePassword))
                }
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = stringResource(R.string.forgotPassword),
            color = HelpingTextColor
        )
        Spacer(
            modifier = Modifier
                .height(22.dp)
        )
        Text(
            modifier = Modifier
                .clickable {
                    onAction(SignIn.Action.OnNavigate(destination = Screen.RestorePassword))
                }
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.dontHaveAnAccount),
            color = DescriptionTextColor
        )
        Spacer(
            modifier = Modifier
                .height(10.dp)
        )

        EFButton(
            modifier = Modifier,
            label = R.string.getGoing,
            onClick = {
                onAction(SignIn.Action.OnLogin)
            }
        )
        Spacer(
            modifier = Modifier
                .height(30.dp)
        )
        EFButton(
            modifier = Modifier,
            image = R.drawable.google,
            label = R.string.continueWithGoogle,
            onClick = {
                onAction(SignIn.Action.OnGoogleLogin)
            }
        )
        Spacer(
            modifier = Modifier.weight(1F)
        )
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