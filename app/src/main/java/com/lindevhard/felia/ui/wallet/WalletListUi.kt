package com.lindevhard.felia.ui.wallet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.lindevhard.felia.R
import com.lindevhard.felia.component.wallet.list.WalletList
import com.lindevhard.felia.ui.components.appbar.AppBarScaffold
import com.lindevhard.felia.ui.components.dialog.AlertDialogButtonsRow
import com.lindevhard.felia.ui.components.dialog.FeliaAlertDialog
import com.lindevhard.felia.ui.wallet.component.WalletRow
import com.lindevhard.felia.ui.R as CommonUI

@Composable
fun WalletListUi(component: WalletList) {
    val state by component.models.subscribeAsState()

    var isShowExitDialog by remember { mutableStateOf(false) }


    if (isShowExitDialog) {
        FeliaAlertDialog(
            title = stringResource(id = R.string.msg_are_you_sure),
            onDismissRequest = { isShowExitDialog = false},
            buttonsContent = {
                AlertDialogButtonsRow(
                    positiveButtonText = stringResource(id = R.string.msg_logout),
                    negativeButtonText = stringResource(id = R.string.msg_cancel),
                    onPositiveClick = { component.exitWallet() },
                    onNegativeClick = { isShowExitDialog = false}
                )
            }
        )
    }

    Column() {
        AppBarScaffold(
            title = stringResource(id = R.string.msg_wallet),
            modifier = Modifier.weight(1f),
            actions = {
                IconButton(onClick = { isShowExitDialog = true }) {
                    Icon(
                        painter = painterResource(id = CommonUI.drawable.ic_fluent_door_arrow_left_24_filled),
                        contentDescription = "Exit",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        ) {
            if(state.isLoading) {
                CircularProgressIndicator()
            } else {
                state.walletList.forEach { wallet ->
                    key(wallet.id) {
                       WalletRow(
                           wallet = wallet,
                           modifier = Modifier
                               .clickable { component.onWalletClick(walletId = wallet.id) }
                               .padding(horizontal = 16.dp, vertical = 4.dp)
                       )
                    }
                }
            }
        }
    }
}