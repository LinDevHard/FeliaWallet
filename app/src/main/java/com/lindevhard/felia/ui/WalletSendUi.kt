@file:OptIn(ExperimentalMaterialApi::class)

package com.lindevhard.felia.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.lindevhard.felia.R
import com.lindevhard.felia.component.wallet.send.WalletSend
import com.lindevhard.felia.ui.components.appbar.AppBarScaffold
import com.lindevhard.felia.ui.components.button.PrimaryButton
import com.lindevhard.felia.ui.components.dialog.AlertDialogButtonsRow
import com.lindevhard.felia.ui.components.dialog.FeliaAlertDialog
import com.lindevhard.felia.ui.theme.mainText
import com.lindevhard.felia.ui.wallet.component.QrScreen
import com.lindevhard.felia.ui.wallet.component.WalletRow
import com.lindevhard.felia.utils.toast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.lindevhard.felia.ui.R as CommonUI

@Composable
fun WalletSendUi(component: WalletSend) {

    val state by component.models.subscribeAsState()
    val context = LocalContext.current
    val scaffoldState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val scope = rememberCoroutineScope()

    if (state.isShowConfirmDialog) {
        FeliaAlertDialog(
            title = stringResource(id = R.string.msg_confirm_transaction),
            subtitle = stringResource(id = R.string.msg_confirm_transaction_desc),
            onDismissRequest = { component.cancelTransaction() },
            image = painterResource(id = com.lindevhard.felia.ui.R.drawable.ic_rocket_launch),
            buttonsContent = {
                AlertDialogButtonsRow(
                    positiveButtonText = stringResource(id = R.string.msg_continue),
                    negativeButtonText = stringResource(id = R.string.msg_cancel),
                    onPositiveClick = { component.startTransaction() },
                    onNegativeClick = { component.cancelTransaction() }
                )
            }
        )
    }

    LaunchedEffect(Unit) {
        component.events.collectLatest { event ->
            when (event) {
                WalletSend.Event.TransactionFailed -> {
                    toast(context, message = R.string.msg_transaction_failed)
                }
                WalletSend.Event.TransactionSuccess -> {
                    component.completeTransaction()
                    toast(context, message = R.string.msg_transaction_success)
                }
            }
        }
    }
    ModalBottomSheetLayout(
        sheetContent = {
            QrScreen(
                onQrCodeRecognized = { address ->
                    component.updateAddress(address)
                    scope.launch {
                        scaffoldState.hide()
                    }
                }
            )
        },
        sheetState = scaffoldState
    ) {

        if (state.isLoading) {
            TransactionProcessLoader()
        } else {

            Column {
                AppBarScaffold(
                    title = stringResource(id = R.string.msg_send) + " ${state.wallet?.symbol ?: ""}",
                    onBackPress = { component.onBackClicked() },
                    modifier = Modifier.weight(1f)
                ) {
                    WalletSendUiMain(
                        component = component,
                        state = state,
                        onQrClick = {
                            scope.launch {
                                scaffoldState.show()
                            }
                        }
                    )
                }


                if (!state.isLoading) {
                    PrimaryButton(
                        text = stringResource(id = R.string.msg_confirm),
                        onClick = { component.clickConfirmButton() },
                        enabled = state.isEnableConfirmButton,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun WalletSendUiMain(
    component: WalletSend,
    state: WalletSend.Model,
    onQrClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column {
        if (state.wallet != null) {
            WalletRow(
                wallet = state.wallet,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            )

            OutlinedTextField(
                value = state.addressInput,
                onValueChange = {
                    component.updateAddress(it)
                },
                label = {
                    Text(text = stringResource(id = R.string.msg_to_address))
                },
                trailingIcon = {
                    IconButton(onClick = onQrClick) {
                        Icon(
                            painter = painterResource(id = CommonUI.drawable.ic_fluent_qr_code_24_filled),
                            contentDescription = "QR code"
                        )
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
            )

            if (!state.addressIsValid) {
                Text(
                    text = stringResource(id = R.string.msg_address_invalid),
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                )
            }

            OutlinedTextField(
                value = state.amountInput,
                onValueChange = {
                    component.updateAmount(getValidatedNumber(it))
                },
                label = {
                    Text(text = stringResource(id = R.string.msg_amount))
                },
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number,
                ),
                singleLine = true,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
            )

            if (!state.amountIsValid) {
                Text(
                    text = stringResource(id = R.string.msg_insufficient_funds),
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                )
            }

        } else {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun TransactionProcessLoader() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()

        Text(
            text = stringResource(id = R.string.msg_transaction_in_progress),
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.mainText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )
    }
}

private fun getValidatedNumber(text: String): String {
    // Start by filtering out unwanted characters like commas and multiple decimals
    val filteredChars = text.filterIndexed { index, c ->
        c in "0123456789" ||                      // Take all digits
                (c == '.' && text.indexOf('.') == index)  // Take only the first decimal
    }

    return filteredChars
}
