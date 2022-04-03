package com.lindevhard.felia.component.wallet.detail

import com.lindevhard.felia.component.wallet.detail.store.WalletDetailStore

val stateToModel: (WalletDetailStore.State) -> WalletDetail.Model =
    { state ->
        if(state.walletDetail != null) {
            WalletDetail.Model.Loaded(state.walletDetail)
        } else {
            WalletDetail.Model.Loading
        }
    }