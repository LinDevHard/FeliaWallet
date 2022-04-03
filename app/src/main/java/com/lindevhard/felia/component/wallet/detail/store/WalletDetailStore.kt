package com.lindevhard.felia.component.wallet.detail.store

import com.arkivanov.mvikotlin.core.store.Store
import com.lindevhard.felia.wallet.main.domain.model.WalletDetailDomain
import com.lindevhard.felia.component.wallet.detail.store.WalletDetailStore.State

interface WalletDetailStore: Store<Nothing, State, Nothing> {

    data class State(
        val walletDetail: WalletDetailDomain? = null,
    )
}