package ua.nure.estateflow.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

@Immutable
data class AppTypography(
    val regularTextStyle: TextStyle = TextStyle.Default,
    val smallTextStyle: TextStyle = TextStyle.Default,
    val largeTextStyle: TextStyle = TextStyle.Default,
)

internal val DarkTypography: AppTypography
    get() = AppTypography(
        regularTextStyle = regularTextStyle,
        smallTextStyle = smallTextStyle,
        largeTextStyle = largeTextStyle
    )

internal val LightTypography: AppTypography
    get() = AppTypography(
        regularTextStyle = regularTextStyle,
        smallTextStyle = smallTextStyle,
        largeTextStyle = largeTextStyle
    )


val regularTextStyle = TextStyle(
    fontSize = 16.sp,
    fontWeight = FontWeight.Normal,
    color = Color.White
)

val smallTextStyle = regularTextStyle.copy(
    fontSize = 12.sp
)

val largeTextStyle = regularTextStyle.copy(
    fontSize = 22.sp,
)