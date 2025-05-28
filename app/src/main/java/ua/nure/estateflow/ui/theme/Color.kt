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

//val Background = Color(0xFF227AC4)
//val BackgroundAi = Color(0xFFFFB854)
//val FocusedTextColor = Color(0xFF227AC4)
//val LabelTextColor = Color(0xFF70A2CD)
//val ButtonTextColor = Color(0xFF227AC4)
//val CheckboxColor = Color(0xFF227AC4)
//val ButtonColor = Color(0xFFFFFFFF)
//val HelpingTextColor = Color(0xFFD1F0FF)
//val DescriptionTextColor = Color(0xFFFFFFFF)
//val ToolBarColor = Color(0xFF87B7DF)

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
)

internal val DarkColors = AppColors(
            primary = DarkSetColors. primary,
            secondary =  DarkSetColors.secondary,
            tertiary = DarkSetColors.tertiary, 
            background = DarkSetColors.background, 
            backgroundAi = DarkSetColors.backgroundAi, 
            focusedTextColor = DarkSetColors.focusedTextColor, 
            labelTextColor = DarkSetColors.labelTextColor, 
            buttonTextColor = DarkSetColors.buttonTextColor, 
            checkboxColor = DarkSetColors.checkboxColor, 
            buttonColor = DarkSetColors.buttonColor, 
            helpingTextColor = DarkSetColors.helpingTextColor, 
            descriptionTextColor = DarkSetColors.descriptionTextColor, 
            toolBarColor = DarkSetColors.toolBarColor, 
)

internal val LightColors = AppColors(
    primary = LightSetColors. primary,
    secondary =  LightSetColors.secondary,
    tertiary = LightSetColors.tertiary,
    background = LightSetColors.background,
    backgroundAi = LightSetColors.backgroundAi,
    focusedTextColor = LightSetColors.focusedTextColor,
    labelTextColor = LightSetColors.labelTextColor,
    buttonTextColor = LightSetColors.buttonTextColor,
    checkboxColor = LightSetColors.checkboxColor,
    buttonColor = LightSetColors.buttonColor,
    helpingTextColor = LightSetColors.helpingTextColor,
    descriptionTextColor = LightSetColors.descriptionTextColor,
    toolBarColor = LightSetColors.toolBarColor,
)

object DarkSetColors {
    val primary =  Purple80
    val secondary = PurpleGrey80 
    val tertiary = Pink80 
    val background = Color(0xFF0F3B5D)
    val backgroundAi = Color(0xFF725224)
    val focusedTextColor = Color(0xFF227AC4) 
    val labelTextColor = Color(0xFF70A2CD) 
    val buttonTextColor = Color(0xFF227AC4) 
    val checkboxColor = Color(0xFF227AC4) 
    val buttonColor = Color(0xFFFFFFFF) 
    val helpingTextColor = Color(0xFFD1F0FF) 
    val descriptionTextColor = Color(0xFFFFFFFF) 
    val toolBarColor = Color(0xFF53748A)
}

object LightSetColors {
    val primary =  Purple40
    val secondary = PurpleGrey40
    val tertiary = Pink40
    val background = Color(0xFF227AC4)
    val backgroundAi = Color(0xFFFFB854)
    val focusedTextColor = Color(0xFF227AC4)
    val labelTextColor = Color(0xFF70A2CD)
    val buttonTextColor = Color(0xFF227AC4)
    val checkboxColor = Color(0xFF227AC4)
    val buttonColor = Color(0xFFFFFFFF)
    val helpingTextColor = Color(0xFFD1F0FF)
    val descriptionTextColor = Color(0xFFFFFFFF)
    val toolBarColor = Color(0xFF87B7DF)
}