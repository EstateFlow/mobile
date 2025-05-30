package ua.nure.estateflow.ui.restore

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import ua.nure.estateflow.ui.components.EFTitlebar
import ua.nure.estateflow.ui.components.EfMainImage
import ua.nure.estateflow.ui.signup.SignUp
import ua.nure.estateflow.ui.theme.AppTheme

@Composable
fun RestoreScreen(
    viewModel: RestoreViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.event.collect {
            when(it) {
                is Restore.Event.OnMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                is Restore.Event.OnNavigate -> {
                    navController.navigate(it.destination)
                }

                Restore.Event.OnBack -> {
                    navController.navigateUp()
                }
            }
        }
    }

    RestoreScreenContent(
        state = state.value,
        onAction = viewModel::onAction
    )

}

@Composable
private fun RestoreScreenContent(
    state: Restore.State,
    onAction: (Restore.Action) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.color.appBackground)
            .padding(top = 32.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        var login by rememberSaveable { mutableStateOf(state.login) }

        EfMainImage(modifier = Modifier)

        EFTextField(
            modifier = Modifier
                .padding(top = AppTheme.dimension.NormalSpace),
            value = login,
            label = R.string.login,
            onValueChange = {
                login = it
                onAction(Restore.Action.OnLoginChanged(login = it))
            }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppTheme.dimension.NormalSpace)
        )
        Text(
            modifier = Modifier
                .clickable{onAction(Restore.Action.OnNavigate(destination = Screen.Auth.SignIn))},
            text = stringResource(R.string.backToLogin),
            style = AppTheme.typography.regularTextStyle.copy(
                color = AppTheme.color.helpingTextColor,
                textAlign = TextAlign.Start
            )
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppTheme.dimension.NormalSpace)
        )
        EFButton(
            modifier = Modifier
                .padding(top = AppTheme.dimension.NormalSpace)
            ,
            label = R.string.resetPassword,
            backgroundColor = AppTheme.color.background,
            textColor = Color.White,
            onClick = {
                onAction(Restore.Action.OnRestorePassword)
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RestoreScreenPreview(modifier: Modifier = Modifier) {
    RestoreScreenContent(
        state = Restore.State(),
        onAction = {}
    )
}