package com.wiseman.wetherapp.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wiseman.wetherapp.R
import com.wiseman.wetherapp.ui.theme.WeatherAppTheme

@Composable
fun WeatherDataDisplay(
    modifier: Modifier = Modifier,
    value: Int,
    unit: String,
    icon: ImageVector,
    textStyle: TextStyle = TextStyle(),
    iconTint: Color = Color.White
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "$value$unit",
            style = textStyle
        )
    }
}

@Preview(
    showBackground = true, showSystemUi = true
)
@Composable
fun PreviewWeatherDataDisplay() {
    WeatherAppTheme {
        WeatherDataDisplay(
            value = 5515,
            unit = "pulvinar",
            icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
        )
    }
}