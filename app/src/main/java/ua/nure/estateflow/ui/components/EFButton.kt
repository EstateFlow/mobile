package ua.nure.estateflow.ui.components

import androidx.annotation.StringRes
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ua.nure.estateflow.R
import ua.nure.estateflow.ui.signup.SignUp
import ua.nure.estateflow.ui.theme.ButtonColor
import ua.nure.estateflow.ui.theme.ButtonTextColor

@Composable
fun EFButton(
    modifier: Modifier = Modifier,
    @StringRes label: Int,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = ButtonColor
        ),
        onClick = {
            onClick()
        }
    ) {
        Text(
            text = stringResource(label),
            color = ButtonTextColor
        )
    }
}