package com.lindevhard.felia.ui.components.appbar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.lindevhard.felia.ui.theme.mainText

@Composable
fun BackArrow(
    painter: Painter,
    modifier: Modifier = Modifier,
    onclick: () -> Unit
) {
    IconButton(onClick = { onclick() }, modifier = modifier) {
        Icon(
            painter = painter,
            "Back Arrow",
            tint = MaterialTheme.colors.mainText
        )
    }
}