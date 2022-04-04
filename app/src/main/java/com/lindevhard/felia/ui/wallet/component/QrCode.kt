package com.lindevhard.felia.ui.wallet.component

import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun QRCode(
    code: String,
    modifier: Modifier = Modifier
) {
    val qr by remember {
        mutableStateOf(
            net.glxn.qrgen.android.QRCode.from(code)
                .withSize(400, 400)
                .withColor(
                    Color.WHITE,
                    Color.TRANSPARENT
                )
                .bitmap()
        )
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .then(modifier)
            .padding(horizontal = 32.dp, vertical = 16.dp)
    ) {

        Image(
            painter = rememberImagePainter(qr),
            contentDescription = "Qr Code",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .size(350.dp)
        )

    }
}
