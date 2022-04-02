package com.lindevhard.felia.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lindevhard.felia.R
import com.lindevhard.felia.component.wallet.list.WalletList
import com.lindevhard.felia.ui.components.appbar.AppBarScaffold

@Composable
fun WalletListUi(component: WalletList) {
    Column() {
        AppBarScaffold(
            title = stringResource(id = R.string.msg_wallet),
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "FeliaMain")
        }
    }
}