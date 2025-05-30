package ua.nure.estateflow.ui.profile.edit

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import ua.nure.estateflow.R
import ua.nure.estateflow.ui.components.EFButton
import ua.nure.estateflow.ui.components.EFTextField
import ua.nure.estateflow.ui.components.EFTitlebar
import ua.nure.estateflow.ui.theme.AppTheme
import ua.nure.estateflow.ui.profile.edit.ProfileEdit.Event
import ua.nure.estateflow.ui.profile.edit.ProfileEdit.State
import ua.nure.estateflow.ui.profile.edit.ProfileEdit.Action

@Composable
fun ProfileEditScreen(
    viewModel: ProfileEditViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.event.collect {
            when(it) {
                Event.OnBack -> navController.navigateUp()
                is Event.OnMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                is Event.OnNavigate -> navController.navigate(it.destination)
            }
        }
    }

    ProfileEditScreenContent(
        state = state.value,
        onAction = viewModel::onAction
    )

}

@Composable
private  fun ProfileEditScreenContent(
    state: State,
    onAction: (Action) -> Unit
) {
    Column(
        modifier = Modifier
            .background(color = AppTheme.color.appBackground)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val context = LocalContext.current
        var avatar by rememberSaveable { mutableStateOf(state.avatarUrl) }
        var username by rememberSaveable { mutableStateOf(state.username) }
        var bio by rememberSaveable { mutableStateOf(state.bio) }

        EFTitlebar(
            modifier = Modifier,
            isBackEnabled = true,
            title = stringResource(R.string.profile),
            onBack = {
                onAction(Action.OnBack)
            },
        )

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
        Spacer(modifier = Modifier.height(AppTheme.dimension.NormalSpace))
        EFTextField(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = AppTheme.dimension.NormalSpace),
            value = avatar,
            label = R.string.avatar,
            onValueChange = {
                onAction(Action.OnAvatarChange(url = it))
                avatar = it
            }
        )
        Spacer(modifier = Modifier.height(AppTheme.dimension.NormalSpace))
        EFTextField(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = AppTheme.dimension.NormalSpace),
            value = username,
            label = R.string.username,
            onValueChange = {
                onAction(Action.OnUsernameChange(username = it))
                username = it
            }
        )
        Spacer(modifier = Modifier.height(AppTheme.dimension.NormalSpace))
        EFTextField(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = AppTheme.dimension.NormalSpace),
            value = bio,
            label = R.string.bio,
            onValueChange = {
                onAction(Action.OnBioChange(bio = it))
                bio = it
            }
        )
        Spacer(modifier = Modifier.weight(1F))

        EFButton(
            modifier = Modifier.padding(
                bottom = WindowInsets
                    .navigationBars
                    .asPaddingValues()
                    .calculateBottomPadding()
            ),
            label = R.string.save,
            onClick = {
                onAction(Action.OnSave)
            }

        )
    }

}

@Preview(showSystemUi = true)
@Composable
private fun ProfileEditScreenPreview(modifier: Modifier = Modifier) {
    ProfileEditScreenContent(
        state = ProfileEdit.State()
    ) { }

}