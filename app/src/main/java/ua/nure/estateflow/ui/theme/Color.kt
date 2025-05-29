package ua.nure.estateflow.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

@Immutable
data class AppColors(
    val primary: Color  = Color.Unspecified,
    val secondary: Color  = Color.Unspecified,
    val tertiary: Color  = Color.Unspecified,
    val background: Color  = Color.Unspecified,
    val backgroundAi: Color  = Color.Unspecified,
    val focusedTextColor: Color  = Color.Unspecified,
    val labelTextColor: Color  = Color.Unspecified,
    val buttonTextColor: Color  = Color.Unspecified,
    val checkboxColor: Color  = Color.Unspecified,
    val buttonColor: Color  = Color.Unspecified,
    val helpingTextColor: Color  = Color.Unspecified,
    val descriptionTextColor: Color  = Color.Unspecified,
    val toolBarColor: Color  = Color.Unspecified,
    val appBackground: Color = Color.Unspecified,
    val controlBackground: Color = Color.Unspecified,
    val mainImageBackground: Color = Color.Unspecified
)

internal val DarkColors = AppColors(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Color(0xFF0F3B5D),
    backgroundAi = Color(0xFF725224),
    focusedTextColor = Color(0xFF227AC4),
    labelTextColor = Color(0xFF70A2CD),
    buttonTextColor = Color(0xFF227AC4),
    checkboxColor = Color(0xFF227AC4),
    buttonColor = Color(0xFFFFFFFF),
    helpingTextColor = Color(0xFFD1F0FF),
    descriptionTextColor = Color(0xFFFFFFFF),
    toolBarColor = Color(0xFF53748A),
    appBackground = Color(0xFF101010),
    controlBackground = Color(0xffFFFFFF),
    mainImageBackground = Color(0xff000000)
)

internal val LightColors = AppColors(
    primary = Purple40,
    secondary =  PurpleGrey40,
    tertiary = Pink40,
    background = Color(0xFF227AC4),
    backgroundAi = Color(0xFFFFB854),
    focusedTextColor = Color(0xFF227AC4),
    labelTextColor = Color(0xFF70A2CD),
    buttonTextColor = Color(0xffFFFFFF),
    checkboxColor = Color(0xFF227AC4),
    buttonColor = Color(0xFF227AC4),
    helpingTextColor = Color(0xFF56616C),
    descriptionTextColor = Color(0xFFFFFFFF),
    toolBarColor = Color(0xFF87B7DF),
    appBackground = Color(0xffFFFFFF),
    controlBackground = Color(0xff000000),
    mainImageBackground = Color(0xFF227AC4)
)
