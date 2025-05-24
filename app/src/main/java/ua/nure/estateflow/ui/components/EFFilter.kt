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
import androidx.compose.runtime.saveable.rememberSaveable
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
    onSearchChanged: (String) -> Unit,
    onRoomsFromChanged: (Int?) -> Unit,
    onRoomsToChanged: (Int?) -> Unit,
    onPriceFromChanged: (Int?) -> Unit,
    onPriceToChanged: (Int?) -> Unit,
    onAreaFromChanged: (Int?) -> Unit,
    onAreaToChanged: (Int?) -> Unit,
    onForRentChanged: (Boolean) -> Unit,
    onForPurchaseChanged: (Boolean) -> Unit,
    onHouseChanged: (Boolean) -> Unit,
    onApartmentChanged: (Boolean) -> Unit,

) {
    var searchField by rememberSaveable { mutableStateOf(search) }

    var roomsFrom by rememberSaveable { mutableStateOf<Int?>(null) }
    var roomsTo by rememberSaveable { mutableStateOf<Int?>(null) }
    var priceFrom by rememberSaveable { mutableStateOf<Int?>(null) }
    var priceTo by rememberSaveable { mutableStateOf<Int?>(null) }
    var areaFrom by rememberSaveable { mutableStateOf<Int?>(null) }
    var areaTo by rememberSaveable { mutableStateOf<Int?>(null) }

    var isForRent by rememberSaveable { mutableStateOf(true) }
    var isForPuchase by rememberSaveable { mutableStateOf(true) }
    var isHouse by rememberSaveable { mutableStateOf(true) }
    var isApartment by rememberSaveable { mutableStateOf(true) }


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
                        value = roomsFrom?.toString() ?: "",
                        label = R.string.from,
                        onValueChange = {
                            it.toIntOrNull().let {
                                roomsFrom = it
                                onRoomsFromChanged(it)
                            }
                        }
                    )
                    EFTextField(
                        modifier = Modifier
                            .weight(1F)
                            .padding(start = MaterialTheme.appDimensions.SmallSpace)
                        ,
                        isPassword = false,
                        value = roomsTo?.toString() ?: "",
                        label = R.string.to,
                        onValueChange = {
                            it.toIntOrNull().let {
                                roomsTo = it
                                onRoomsToChanged(it)
                            }
                        }
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
                        value = priceFrom?.toString() ?: "",
                        label = R.string.from,
                        onValueChange = {
                            it.toIntOrNull().let {
                                priceFrom = it
                                onPriceFromChanged(it)
                            }
                        }
                    )
                    EFTextField(
                        modifier = Modifier
                            .weight(1F)
                            .padding(start = MaterialTheme.appDimensions.SmallSpace)
                        ,
                        isPassword = false,
                        value = priceTo?.toString() ?: "",
                        label = R.string.to,
                        onValueChange = {
                            it.toIntOrNull().let {
                                priceTo = it
                                onPriceToChanged(it)
                            }
                        }
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
                        value = areaFrom?.toString() ?: "",
                        label = R.string.from,
                        onValueChange = {
                            it.toIntOrNull().let {
                                areaFrom = it
                                onAreaFromChanged(it)
                            }
                        }
                    )
                    EFTextField(
                        modifier = Modifier
                            .weight(1F)
                            .padding(start = MaterialTheme.appDimensions.SmallSpace)
                        ,
                        isPassword = false,
                        value = areaTo?.toString() ?: "",
                        label = R.string.to,
                        onValueChange = {
                            it.toIntOrNull().let {
                                areaTo = it
                                onAreaToChanged(it)
                            }
                        }
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
                        checked = isForRent,
                        onCheckedChange = {
                            isForRent = it
                            onForRentChanged(it)

                        },
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
                        checked = isForPuchase,
                        onCheckedChange = {
                            isForPuchase = it
                            onForPurchaseChanged(it)
                        },
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
                        checked = isHouse,
                        onCheckedChange = {
                            isHouse = it
                            onHouseChanged(it)
                        },
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
                        checked = isApartment,
                        onCheckedChange = {
                            isApartment = it
                            onApartmentChanged(it)
                        },
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
        onSearchChanged = {},
        onRoomsToChanged = {},
        onRoomsFromChanged = {},
        onAreaFromChanged = {},
        onAreaToChanged = {},
        onPriceFromChanged = {},
        onPriceToChanged = {},
        onForRentChanged = {},
        onForPurchaseChanged = {},
        onHouseChanged = {},
        onApartmentChanged = {}
        )
}