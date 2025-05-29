package ua.nure.estateflow.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalAppColorScheme = staticCompositionLocalOf { AppColors() }
val LocalAppTypography = staticCompositionLocalOf { AppTypography() }
val LocalAppDimension = staticCompositionLocalOf { AppDimension() }
val LocalAppShape = staticCompositionLocalOf { AppShape() }

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val color = if(darkTheme) DarkColors else LightColors
    val typography = if(darkTheme) DarkTypography else LightTypography
    val dimension = AppDimension()
    val shape = AppShape()

    CompositionLocalProvider(
        LocalAppColorScheme provides color,
        LocalAppTypography provides typography,
        LocalAppDimension provides dimension,
        LocalAppShape provides shape,
        content = content
    )
}

object AppTheme {
    val color: AppColors
        @Composable get() = LocalAppColorScheme.current

    val typography: AppTypography
        @Composable get() = LocalAppTypography.current

    val dimension: AppDimension
        @Composable get() = LocalAppDimension.current

    val shape: AppShape
        @Composable get() = LocalAppShape.current
}


