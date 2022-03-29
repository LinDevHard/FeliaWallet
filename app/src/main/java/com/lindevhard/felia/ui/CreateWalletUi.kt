package com.lindevhard.felia.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.lindevhard.felia.R
import com.lindevhard.felia.component.create_wallet.CreateWallet
import com.lindevhard.felia.ui.components.appbar.AppBarScaffold
import com.lindevhard.felia.ui.components.button.PrimaryButton
import com.lindevhard.felia.ui.theme.mainText

@Composable
fun CreateWalletUi(component: CreateWallet) {
    Column() {
        AppBarScaffold(
            title = stringResource(id = R.string.msg_create_wallet),
            onBackPress = { component.onBackPressed() },
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(id = R.string.msg_user_create_wallet),
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.mainText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 24.dp)
            )
        }

        PrimaryButton(
            text = stringResource(id = R.string.msg_create_wallet),
            onClick = { component.onCreateWalletClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .navigationBarsPadding()
        )
    }
}