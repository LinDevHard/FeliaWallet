package com.lindevhard.felia.ui.wallet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.lindevhard.felia.R
import com.lindevhard.felia.component.wallet.list.WalletList
import com.lindevhard.felia.ui.components.appbar.AppBarScaffold
import com.lindevhard.felia.ui.wallet.component.WalletRow

@Composable
fun WalletListUi(component: WalletList) {
    val state by component.models.subscribeAsState()

    Column() {
        AppBarScaffold(
            title = stringResource(id = R.string.msg_wallet),
            modifier = Modifier.weight(1f)
        ) {
            if(state.isLoading) {
                CircularProgressIndicator()
            } else {
                state.walletList.forEach { wallet ->
                    key(wallet.id) {
                       WalletRow(
                           wallet = wallet,
                           modifier = Modifier
                               .padding(horizontal = 16.dp, vertical = 4.dp)
                       )
                    }
                }
            }
        }
    }
}