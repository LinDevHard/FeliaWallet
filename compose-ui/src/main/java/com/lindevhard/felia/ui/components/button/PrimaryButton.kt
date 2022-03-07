package com.lindevhard.felia.ui.components.button

import androidx.compose.foundation.layout.padding
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
import com.lindevhard.felia.ui.theme.disabledButton
import com.lindevhard.felia.ui.theme.disabledTextColor
import com.lindevhard.felia.ui.theme.white

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: Painter? = null,
    iconTint: Color = MaterialTheme.colors.white,
    textColor: Color = Color.White,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        disabledBackgroundColor = MaterialTheme.colors.disabledButton
    ),
) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        enabled = enabled,
        modifier = modifier,
        colors = colors
    ) {
        icon?.let { painter ->
            Icon(
                painter = painter,
                contentDescription = null,
                tint = iconTint
            )
        }

        Text(
            text = text,
            style = MaterialTheme.typography.button,
            color = if (enabled) textColor else MaterialTheme.colors.disabledTextColor,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}