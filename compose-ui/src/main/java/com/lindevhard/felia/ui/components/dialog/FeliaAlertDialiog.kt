package com.lindevhard.felia.ui.components.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.lindevhard.felia.ui.R
import com.lindevhard.felia.ui.components.button.PrimaryButton
import com.lindevhard.felia.ui.components.button.SecondaryButton
import com.lindevhard.felia.ui.theme.mainText


@Composable
fun FeliaAlertDialog(
    title: String,
    subtitle: String,
    onDismissRequest: () -> Unit,
    image: Painter = painterResource(id = R.drawable.ic_alert),
    buttonsContent: @Composable () -> Unit = {}
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            shape = RoundedCornerShape(8.dp),
            backgroundColor = MaterialTheme.colors.surface
        ) {

            Column(
                modifier = Modifier
                    .padding(horizontal = 14.dp, vertical = 24.dp)
            ) {

                Image(
                    painter = image,
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .size(60.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Text(
                    text = title,
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.mainText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.mainText.copy(alpha = 0.42f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(36.dp))

                buttonsContent()
            }
        }
    }
}

@Composable
fun AlertDialogButtonsRow(
    positiveButtonText: String,
    negativeButtonText: String,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit,
) {
    Row() {
        PrimaryButton(
            text = positiveButtonText,
            onClick = { onPositiveClick() },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(end = 4.dp)
        )

        SecondaryButton(
            text = negativeButtonText,
            onClick = { onNegativeClick() },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 4.dp)
        )
    }
}