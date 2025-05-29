package ua.nure.estateflow.ui.components

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ua.nure.estateflow.R
import ua.nure.estateflow.ui.theme.AppTheme

@Composable
fun EfGalleryIndicator(
    modifier: Modifier = Modifier,
    selected: Int,
    total: Int,
    regularSize: Dp = 12.dp,
    activeSize: Dp = 14.dp,
    height: Dp = 24.dp,
    @DrawableRes active: Int,
    @DrawableRes passive: Int
) {
    Row(
        modifier = modifier
            .height(height),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        (0..total -1).forEachIndexed { index, item ->
            Icon(
                modifier = Modifier
                    .size(if (index == selected) activeSize else regularSize)
                ,
                painter = painterResource(if(index == selected) active else passive),
                contentDescription = "",
                tint = AppTheme.color.controlBackground
            )
        }

    }
}