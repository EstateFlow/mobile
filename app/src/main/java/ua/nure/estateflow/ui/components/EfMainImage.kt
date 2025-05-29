package ua.nure.estateflow.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.nure.estateflow.R
import ua.nure.estateflow.ui.theme.AppTheme

@Composable
fun EfMainImage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(top = AppTheme.dimension.SmallSpace)
            .size(220.dp)
            .background(color = AppTheme.color.mainImageBackground, shape = CircleShape)
            .clip(CircleShape)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 10.dp)
            ,
            painter = painterResource(if(isSystemInDarkTheme()) R.drawable.main_image_dark else R.drawable.main_image_light),
            contentDescription = ""
        )
        Text(
            modifier = Modifier
                .offset(
                    y = -8.dp
                )
            ,
            text = "EstateFlow",
            style = AppTheme.typography.largeTextStyle.copy(
                fontSize = 26.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )
    }
    
}

@Preview(showBackground = true)
@Composable
private fun EfMainImagePreview(modifier: Modifier = Modifier) {
    EfMainImage()

}