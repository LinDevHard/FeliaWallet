package com.lindevhard.felia.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.lindevhard.felia.R
import com.lindevhard.felia.ui.R as CommonUI
import com.lindevhard.felia.component.import_wallet.ImportWallet
import com.lindevhard.felia.ui.components.appbar.AppBarScaffold
import com.lindevhard.felia.ui.components.button.PrimaryButton
import com.lindevhard.felia.ui.components.dialog.FeliaAlertDialog
import com.lindevhard.felia.ui.theme.mainText

@Composable
fun ImportWalletUi(component: ImportWallet) {

    val state by component.models.subscribeAsState()

    if (state.isImported) {
        FeliaAlertDialog(
            title = stringResource(id = R.string.msg_import_wallet_imported),
            onDismissRequest = { component.onCompleteImport() },
            image = painterResource(id = CommonUI.drawable.ic_rocket_launch),
            buttonsContent = {
                PrimaryButton(
                    text = stringResource(id = R.string.msg_ok),
                    onClick = { component.onCompleteImport() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
        )
    }

    Column(modifier = Modifier.navigationBarsWithImePadding()) {
        AppBarScaffold(
            title = stringResource(id = R.string.msg_import_wallet),
            onBackPress = { component.onBackPressed() },
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(id = R.string.msg_user_import_wallet),
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.mainText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 24.dp)
            )

            OutlinedTextField(
                value = state.seed,
                onValueChange = { component.onTextChanged(it) },
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
            )

            if (state.isError) {
                Text(
                    text = stringResource(id = R.string.msg_import_wallet_error),
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                )
            }
        }

        PrimaryButton(
            text = stringResource(id = R.string.msg_import_wallet),
            onClick = { component.onImportWalletClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}