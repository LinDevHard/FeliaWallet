package com.lindevhard.felia.ui.wallet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.lindevhard.felia.R
import com.lindevhard.felia.component.wallet.receive.WalletReceive
import com.lindevhard.felia.ui.components.appbar.AppBarScaffold
import com.lindevhard.felia.ui.components.button.DialogFlushButton
import com.lindevhard.felia.ui.wallet.component.QRCode
import com.lindevhard.felia.utils.copyText
import com.lindevhard.felia.utils.shareText
import com.lindevhard.felia.utils.toast

@Composable
fun WalletReceiveUi(component: WalletReceive) {
    val context = LocalContext.current
    val state by component.models.subscribeAsState()
    val appTitle =
        if (state.wallet == null)
            stringResource(id = R.string.msg_receive)
        else
            stringResource(id = R.string.msg_receive) + " ${state.wallet?.symbol}"
    val copiedText = stringResource(id = R.string.msg_copied)

    Column {
        AppBarScaffold(
            title = appTitle,
            onBackPress = { component.onBackClicked() },
            modifier = Modifier.weight(1f)
        ) {

            Spacer(modifier = Modifier.height(36.dp))

            if(state.wallet != null) {
                val address = state.wallet?.address ?: ""
                QRCode(
                    code = address,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 4.dp)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DialogFlushButton(
                        text = stringResource(id = R.string.msg_copy_address),
                        onClick = {
                            context.copyText(address)
                            context.toast(copiedText)
                        },
                        icon = painterResource(id = com.lindevhard.felia.ui.R.drawable.ic_copy),
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    )

                    DialogFlushButton(
                        text = stringResource(id = R.string.msg_share_address),
                        onClick = {
                            context.shareText(address)
                        },
                        icon = painterResource(id = com.lindevhard.felia.ui.R.drawable.ic_share),
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}