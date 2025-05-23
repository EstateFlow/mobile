package ua.nure.estateflow.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import ua.nure.estateflow.R
import ua.nure.estateflow.ui.theme.Background
import ua.nure.estateflow.ui.theme.CheckboxColor
import ua.nure.estateflow.ui.theme.ToolBarColor
import ua.nure.estateflow.ui.theme.appDimensions
import ua.nure.estateflow.ui.theme.regularTextStyle
import kotlin.to

@Composable
fun EFFilter(
    modifier: Modifier = Modifier,
    search: String = "",
    onSearchChanged: (String) -> Unit = {}
) {
    var searchField by remember {
        mutableStateOf(search)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = ToolBarColor,
                shape = RoundedCornerShape(MaterialTheme.appDimensions.Radius)
            )
            .padding(all = MaterialTheme.appDimensions.SmallSpace)
    ) {
        EFTextField(
            modifier = Modifier,
            isPassword = false,
            value = searchField,
            label = R.string.search,
            onValueChange = {
                searchField = it
                onSearchChanged(it)
            }
        )
        Row(
            modifier = Modifier
        ) {
            Column(
                modifier = Modifier.weight(1F)
            ) {
                Text(
                    text = stringResource(R.string.rooms),
                    style = regularTextStyle.copy(
                        color = Color.Black
                    )
                )
                Row(
                    modifier = Modifier
                ) {
                    EFTextField(
                        modifier = Modifier
                            .weight(1F)
                        ,
                        isPassword = false,
                        value = "",
                        label = R.string.from,
                        onValueChange = {}
                    )
                    EFTextField(
                        modifier = Modifier
                            .weight(1F)
                            .padding(start = MaterialTheme.appDimensions.SmallSpace)
                        ,
                        isPassword = false,
                        value = "",
                        label = R.string.to,
                        onValueChange = {}
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(top = MaterialTheme.appDimensions.SmallSpace),
                    text = stringResource(R.string.price),
                    style = regularTextStyle.copy(
                        color = Color.Black
                    )
                )
                Row(
                    modifier = Modifier
                ) {
                    EFTextField(
                        modifier = Modifier
                            .weight(1F)
                        ,
                        isPassword = false,
                        value = "",
                        label = R.string.from,
                        onValueChange = {}
                    )
                    EFTextField(
                        modifier = Modifier
                            .weight(1F)
                            .padding(start = MaterialTheme.appDimensions.SmallSpace)
                        ,
                        isPassword = false,
                        value = "",
                        label = R.string.to,
                        onValueChange = {}
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(top = MaterialTheme.appDimensions.SmallSpace),
                    text = stringResource(R.string.area),
                    style = regularTextStyle.copy(
                        color = Color.Black
                    )
                )
                Row(
                    modifier = Modifier
                ) {
                    EFTextField(
                        modifier = Modifier
                            .weight(1F)
                        ,
                        isPassword = false,
                        value = "",
                        label = R.string.from,
                        onValueChange = {}
                    )
                    EFTextField(
                        modifier = Modifier
                            .weight(1F)
                            .padding(start = MaterialTheme.appDimensions.SmallSpace)
                        ,
                        isPassword = false,
                        value = "",
                        label = R.string.to,
                        onValueChange = {}
                    )
                }

            }
            Column(
                modifier = Modifier.weight(1F)
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Checkbox(
                        checked = true,
                        onCheckedChange = {},
                        colors = CheckboxDefaults.colors().copy(
                            checkedBoxColor = CheckboxColor,
                            checkedBorderColor = CheckboxColor
                        ),
                    )
                    Text(
                        text = stringResource(R.string.forRent)
                    )
                }

                Row(
                    modifier = Modifier
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Checkbox(
                        checked = true,
                        onCheckedChange = {},
                        colors = CheckboxDefaults.colors().copy(
                            checkedBoxColor = CheckboxColor,
                            checkedBorderColor = CheckboxColor
                        ),
                    )
                    Text(
                        text = stringResource(R.string.forPurchase)
                    )
                }

                Row(
                    modifier = Modifier
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Checkbox(
                        checked = true,
                        onCheckedChange = {},
                        colors = CheckboxDefaults.colors().copy(
                            checkedBoxColor = CheckboxColor,
                            checkedBorderColor = CheckboxColor
                        ),
                    )
                    Text(
                        text = stringResource(R.string.house)
                    )
                }

                Row(
                    modifier = Modifier
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Checkbox(
                        checked = true,
                        onCheckedChange = {},
                        colors = CheckboxDefaults.colors().copy(
                            checkedBoxColor = CheckboxColor,
                            checkedBorderColor = CheckboxColor
                        ),
                    )
                    Text(
                        text = stringResource(R.string.apartment)
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun EFFilterPreview(modifier: Modifier = Modifier) {
    EFFilter(
        modifier = modifier,

        )
}