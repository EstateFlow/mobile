package ua.nure.estateflow.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import ua.nure.estateflow.R
import ua.nure.estateflow.ui.theme.AppTheme

@Composable
fun OutlinedPasswordTextField(
    modifier: Modifier = Modifier,
    password: String,
    @StringRes label: Int,
    onValueChange: (String) -> Unit
) {
    var _password by rememberSaveable { mutableStateOf(password) }

    OutlinedTextField(
        modifier = modifier,
        value = _password,
        onValueChange = {
            _password = it
            onValueChange(it)
        },
        colors = TextFieldDefaults.colors()
            .copy(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = AppTheme.color.focusedTextColor,
                unfocusedTextColor = AppTheme.color.labelTextColor,
                unfocusedLabelColor = AppTheme.color.focusedTextColor,
            )
        ,
        label = {
            Text(text = stringResource(label))
        },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@Preview(showBackground = true)
@Composable
fun PasswordTextFieldPreview(modifier: Modifier = Modifier) {
    OutlinedPasswordTextField(
        password = "",
        label = R.string.password,
        onValueChange = {}
    )
}