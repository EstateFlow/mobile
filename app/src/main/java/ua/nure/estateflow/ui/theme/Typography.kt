package ua.nure.estateflow.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class AppTypography(
    val regularTextStyle: TextStyle = TextStyle.Default,
    val regularContrastTextStyle: TextStyle = TextStyle.Default,
    val smallTextStyle: TextStyle = TextStyle.Default,
    val smallContrastTextStyle: TextStyle = TextStyle.Default,
    val largeTextStyle: TextStyle = TextStyle.Default,
)

internal val DarkTypography: AppTypography
    get() = AppTypography(
        regularTextStyle = regularTextStyle.copy(
            color = Color.White
        ),
        regularContrastTextStyle = regularTextStyle,
        smallTextStyle = smallTextStyle.copy(
            color = Color.White
        ),
        smallContrastTextStyle = smallTextStyle,
        largeTextStyle = largeTextStyle.copy(
            color = Color.White
        )
    )

internal val LightTypography: AppTypography
    get() = AppTypography(
        regularTextStyle = regularTextStyle,
        regularContrastTextStyle = regularTextStyle.copy(
            color = Color.White
        ),
        smallContrastTextStyle = smallTextStyle.copy(
            color = Color.White
        ),
        smallTextStyle = smallTextStyle,
        largeTextStyle = largeTextStyle
    )


private val regularTextStyle = TextStyle(
    fontSize = 16.sp,
    fontWeight = FontWeight.Normal,
    color = Color.Black
)

private val smallTextStyle = regularTextStyle.copy(
    fontSize = 12.sp
)

private val largeTextStyle = regularTextStyle.copy(
    fontSize = 22.sp,
)