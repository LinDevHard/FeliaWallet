package com.lindevhard.felia.component.wallet.receive

import com.lindevhard.felia.component.wallet.detail.store.WalletDetailStore

val stateToModel: (WalletDetailStore.State) -> WalletReceive.Model =
    { state ->
        if(state.walletDetail != null) {
            WalletReceive.Model(
                wallet = state.walletDetail,
                isLoading = true
            )
        } else {
            WalletReceive.Model(
                wallet = state.walletDetail,
                isLoading = false
            )
        }
    }