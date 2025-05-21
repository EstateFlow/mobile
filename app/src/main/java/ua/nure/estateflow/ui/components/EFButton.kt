package ua.nure.estateflow.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.nure.estateflow.R
import ua.nure.estateflow.ui.signup.SignUp
import ua.nure.estateflow.ui.theme.ButtonColor
import ua.nure.estateflow.ui.theme.ButtonTextColor

@Composable
fun EFButton(
    modifier: Modifier = Modifier,
    @StringRes label: Int,
    @DrawableRes image: Int? = null,
    backgroundColor: Color = ButtonColor,
    textColor: Color = ButtonTextColor,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = backgroundColor
        ),
        onClick = {
            onClick()
        }
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(label),
                color = textColor
            )
            image?.let {
                Image(
                    modifier = Modifier
                        .size(36.dp),
                    painter = painterResource(it),
                    contentDescription = ""
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EFButtonPreview() {
    EFButton(
        modifier = Modifier,
        label = R.string.login,
        image = R.drawable.google
    ) { }
}