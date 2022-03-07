package com.lindevhard.felia.ui.components.button

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun DialogFlushButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: Painter? = null,
) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        enabled = enabled,
        modifier = modifier,
        elevation = ButtonDefaults
            .elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            ),
        colors = ButtonDefaults.textButtonColors()
    ) {
        icon?.let { painter ->
            Icon(painter = painter, contentDescription = null)
        }

        Text(
            text = text,
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}