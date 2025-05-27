package ua.nure.estateflow.ui.signin

import android.util.Log
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
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ua.nure.estateflow.R
import ua.nure.estateflow.config.WEB_CLIENT_ID
import ua.nure.estateflow.navigation.Screen
import ua.nure.estateflow.ui.components.EFButton
import ua.nure.estateflow.ui.components.EFTextField
import ua.nure.estateflow.ui.signup.SignUp
import ua.nure.estateflow.ui.theme.DescriptionTextColor
import ua.nure.estateflow.ui.theme.FocusedTextColor
import ua.nure.estateflow.ui.theme.HelpingTextColor

private val TAG = "SignInScreen"

@Composable
fun SignInScreen(
    viewModel: SignInViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val credentialManager = remember { CredentialManager.create(context = context) }

    LaunchedEffect(key1 = Unit) {
        viewModel.event.collect {
            when(it) {
                is SignIn.Event.OnMessage -> Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                is SignIn.Event.OnNavigate -> {
                    Log.d(TAG, "SignInScreen: ${it.destination}")
                    navController.navigate(it.destination)
                }
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
        val context = LocalContext.current
        val credentialManager = remember { CredentialManager.create(context = context) }

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
//                onAction(SignIn.Action.OnGoogleLogin)

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val googleIdOption = GetGoogleIdOption.Builder()
                            .setFilterByAuthorizedAccounts(false)
                            .setServerClientId(WEB_CLIENT_ID)
                            .build()

                        val request = GetCredentialRequest.Builder()
                            .addCredentialOption(googleIdOption)
                            .build()

                        val result: GetCredentialResponse = credentialManager.getCredential(
                            request = request,
                            context = context
                        )

                        val credential = result.credential

                        when(credential) {
                            // Passkey credential
                           is PublicKeyCredential -> {
                               // Share responseJson such as a GetCredentialResponse on your server to
                               // validate and authenticate
                               val responseJson = credential.authenticationResponseJson
                           }
                            // Password credential
                            is PasswordCredential -> {
                                // Send ID and password to your server to validate and authenticate.
                                val username = credential.id
                                val password = credential.password
                            }
                            // GoogleIdToken credential
                            is CustomCredential -> {
                                if(credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                                    try {
                                        // Use googleIdTokenCredential and extract the ID to validate and
                                        // authenticate on your server.
                                        val googleIdTokenCredential = GoogleIdTokenCredential
                                            .createFrom(credential.data)
                                        // You can use the members of googleIdTokenCredential directly for UX
                                        // purposes, but don't use them to store or control access to user
                                        // data. For that you first need to validate the token:
                                        // pass googleIdTokenCredential.getIdToken() to the backend server.
//                                        GoogleIdTokenVerifier verifier = ... // see validation instructions
//                                        GoogleIdToken idToken = verifier.verify(idTokenString);
                                        // To get a stable account identifier (e.g. for storing user data),
                                        // use the subject ID:
//                                        idToken.getPayload().getSubject()

                                        Log.d(TAG, "SignInScreenContent: Google idToken: ${googleIdTokenCredential.idToken}")

                                    } catch (ex: GoogleIdTokenParsingException) {
                                        Log.e(TAG, "SignInScreenContent: Received an invalid google id token response", ex)
                                    }
                                } else {
                                    Log.e(TAG, "Unexpected type of credential")
                                }
                            }
                            else -> {
                                Log.e(TAG, "Unexpected type of credential")
                            }

                        }



                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
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