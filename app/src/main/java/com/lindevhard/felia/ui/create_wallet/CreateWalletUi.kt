package com.lindevhard.felia.ui.create_wallet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.insets.navigationBarsPadding
import com.lindevhard.felia.R
import com.lindevhard.felia.component.create_wallet.CreateWallet
import com.lindevhard.felia.ui.components.appbar.AppBarScaffold
import com.lindevhard.felia.ui.components.button.DialogFlushButton
import com.lindevhard.felia.ui.components.button.PrimaryButton
import com.lindevhard.felia.ui.components.dialog.FeliaAlertDialog
import com.lindevhard.felia.ui.create_wallet.component.WordChip
import com.lindevhard.felia.ui.theme.mainText
import com.lindevhard.felia.utils.copyText
import com.lindevhard.felia.utils.toast
import com.lindevhard.felia.ui.R as CommonUI

@Composable
fun CreateWalletUi(component: CreateWallet) {

    val state by component.models.subscribeAsState()
    val context = LocalContext.current
    val copiedText = stringResource(id = R.string.msg_copied)

    Column() {

        if (state.isCreated) {
            FeliaAlertDialog(
                title = stringResource(id = R.string.msg_create_wallet_created),
                onDismissRequest = { component.onCompleteCreated() },
                image = painterResource(id = CommonUI.drawable.ic_rocket_launch),
                buttonsContent = {
                    PrimaryButton(
                        text = stringResource(id = R.string.msg_ok),
                        onClick = { component.onCompleteCreated() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
            )
        }

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

            FlowRow(
                mainAxisAlignment = FlowMainAxisAlignment.Center,
                mainAxisSpacing = 6.dp,
                crossAxisSpacing = 5.dp,
                modifier = Modifier
                    .sizeIn(minHeight = 150.dp)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface, MaterialTheme.shapes.medium)
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                state.wordList.forEach {
                    WordChip(title = it)
                }
            }

            DialogFlushButton(
                text = stringResource(id = R.string.msg_copy_seed),
                onClick = {
                    context.copyText(state.seedPhrase)
                    context.toast(copiedText)
                },
                icon = painterResource(id = CommonUI.drawable.ic_copy),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
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