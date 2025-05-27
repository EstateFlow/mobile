package ua.nure.estateflow.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppDimension(
    val IconSize: Dp = 24.dp,
    val ActionSize: Dp = 36.dp,
    val SmallSpace: Dp = 8.dp,
    val NormalSpace: Dp = 16.dp,
    val LargeSpace: Dp = 32.dp,
    val Radius: Dp = 10.dp,
    val ChatRadius: Dp = 15.dp
)

val LocalAppDimension = compositionLocalOf {
    AppDimension()
}

val MaterialTheme.appDimensions: AppDimension
    @Composable
    @ReadOnlyComposable
    @Stable
    get() = LocalAppDimension.current