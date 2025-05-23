package ua.nure.estateflow.ui.components

import android.R.attr.name
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import ua.nure.estateflow.R
import ua.nure.estateflow.ui.theme.FocusedTextColor
import ua.nure.estateflow.ui.theme.LabelTextColor

@Composable
fun EFTextField(
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    value: String,
    @StringRes label: Int,
    onValueChange: (String) -> Unit
) {
    var _value by remember { mutableStateOf(value) }
    if(isPassword) {
        OutlinedPasswordTextField(
            modifier = modifier,
            password = _value,
            onValueChange = {
                onValueChange(it)
            },
            label = label
        )
    } else {
        OutlinedTextField(
            modifier = modifier,
            value = _value,
            colors = TextFieldDefaults.colors()
                .copy(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedTextColor = FocusedTextColor,
                    unfocusedTextColor = LabelTextColor,
                    unfocusedLabelColor = FocusedTextColor,

                    ),
            label = {
                Text(text = stringResource(label))
            },
            onValueChange = {
                _value = it
                onValueChange(it)

            }
        )
    }

}