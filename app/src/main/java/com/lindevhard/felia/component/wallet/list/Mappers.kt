package com.lindevhard.felia.component.wallet.list

import com.lindevhard.felia.component.main.store.WalletMainStore

val stateToModel: (WalletMainStore.State) -> WalletList.Model =
    { state ->
        WalletList.Model(
            walletList = state.wallets,
            isLoading = state.isLoading
        )
    }