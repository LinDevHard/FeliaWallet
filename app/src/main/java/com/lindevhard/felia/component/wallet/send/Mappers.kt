package com.lindevhard.felia.component.wallet.send

import com.lindevhard.felia.component.wallet.send.store.WalletSendStore


val stateToModel: (WalletSendStore.State) -> WalletSend.Model =
    {
        WalletSend.Model(
            wallet = it.wallet,
            addressInput = it.addressInput,
            addressIsValid = it.addressIsValid,
            amountInput = it.amountInput,
            amountIsValid = it.amountIsValid,
            isEnableConfirmButton = it.addressIsValid && it.amountIsValid &&
                    it.amountInput.isNotEmpty() && it.addressInput.isNotEmpty(),
            isLoading = it.isLoading,
            isShowConfirmDialog = it.isShowConfirmDialog
        )
    }

val labelToEvent: (WalletSendStore.Label) -> WalletSend.Event =
    {
        when(it) {
            is WalletSendStore.Label.TransactionError -> {
                WalletSend.Event.TransactionFailed
            }
            WalletSendStore.Label.TransactionSuccess ->
                WalletSend.Event.TransactionSuccess
        }
    }