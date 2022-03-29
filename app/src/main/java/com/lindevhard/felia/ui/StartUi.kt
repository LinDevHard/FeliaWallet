package com.lindevhard.felia.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.lindevhard.felia.R
import com.lindevhard.felia.component.start.FeliaStart
import com.lindevhard.felia.ui.components.button.DialogFlushButton
import com.lindevhard.felia.ui.components.button.PrimaryButton

@Composable
fun StartUi(component: FeliaStart) {

    Column() {
        Image(
            painter = painterResource(id = R.drawable.ic_small_logo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 32.dp)
                .statusBarsPadding()
        )

        Image(
            painter = painterResource(id = R.drawable.ic_big_wallet_logo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 32.dp)
        )
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .navigationBarsPadding()
        ) {

            PrimaryButton(
                text = stringResource(id = R.string.msg_enter_create_wallet),
                onClick = { component.onCreateWalletClicked() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            DialogFlushButton(
                text = stringResource(id = R.string.msg_already_have_wallet),
                onClick = { component.onImportWalletClicked() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}