package com.lindevhard.felia.component.wallet.receive

import com.lindevhard.felia.component.wallet.detail.store.WalletDetailStore

val stateToModel: (WalletDetailStore.State) -> WalletReceive.Model =
    { state ->
        if(state.walletDetail != null) {
            WalletReceive.Model(
                symbol = "",
                name = "",
                address = "",
                isLoading = true
            )
        } else {
            WalletReceive.Model(
                symbol = state.walletDetail?.symbol ?: "",
                name = state.walletDetail?.name ?: "",
                address = state.walletDetail?.address ?: "",
                isLoading = false
            )
        }
    }