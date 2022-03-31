package com.lindevhard.felia.ui.create_wallet.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.lindevhard.felia.ui.theme.mainText

@Composable
fun WordChip(
    title: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    Box(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.mainText,
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .clickable(enabled = onClick != null) { onClick?.invoke() }
                .border(
                    1.dp,
                    color = Color(0xFF9A9A9A),
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(horizontal = 28.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun WordNumberChip(
    word: String,
    number: Int,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Box(modifier = modifier) {

        val annotatedString = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MaterialTheme.colors.mainText)) {
                append("$number")
            }

            append(".$word")
        }

        Text(
            text = annotatedString,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.mainText,
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .clickable(enabled = onClick != null) { onClick?.invoke() }
                .background(
                    color = MaterialTheme.colors.background,
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(horizontal = 28.dp, vertical = 6.dp)
        )
    }
}