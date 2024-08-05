package com.angga.pokedex.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.angga.pokedex.presentation.ui.theme.InActiveItem
import com.angga.pokedex.presentation.ui.theme.SelectedItem

@Composable
fun MenuItem(
    modifier: Modifier,
    label : String,
    drawableRes: Int,
    isSelected : Boolean = false,
    onClick : () -> Unit
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .clip(RoundedCornerShape(34.dp))
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(4.dp))

        Image(
            painter = painterResource(id = drawableRes),
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                if (isSelected) { SelectedItem } else { InActiveItem }
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            color = if (isSelected) { SelectedItem } else { InActiveItem }
        )
    }
}