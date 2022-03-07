package com.lindevhard.felia.ui.components.button

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.lindevhard.felia.ui.theme.mainText
import com.lindevhard.felia.ui.theme.secondaryButton
import com.lindevhard.felia.ui.theme.secondaryText
import com.lindevhard.felia.ui.theme.white

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: Painter? = null,
    iconTint: Color = MaterialTheme.colors.white,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = MaterialTheme.colors.secondaryButton
    ),
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        enabled = enabled,
        colors = colors,
        modifier = modifier,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
        )
    ) {

        icon?.let { painter ->
            Icon(
                painter = painter,
                contentDescription = null,
                tint = iconTint,
            )
        }

        Text(
            text = text,
            style = MaterialTheme.typography.button,
            color = if (enabled)
                MaterialTheme.colors.mainText
            else
                MaterialTheme.colors.secondaryText,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}