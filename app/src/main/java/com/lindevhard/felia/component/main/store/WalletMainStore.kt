package com.lindevhard.felia.component.main.store

import com.arkivanov.mvikotlin.core.store.Store
import com.lindevhard.felia.wallet.main.domain.model.Wallet
import com.lindevhard.felia.component.main.store.WalletMainStore.Intent
import com.lindevhard.felia.component.main.store.WalletMainStore.State
import com.lindevhard.felia.component.main.store.WalletMainStore.Label


interface WalletMainStore: Store<Intent, State, Label> {

    sealed class Intent {
        object WalletExit: Intent()
        object RefreshWallet: Intent()
        class RefreshWalletById(val walletIds: List<Long>): Intent()
    }

    data class State(
        val wallets: List<Wallet> = listOf(),
        val isLoading: Boolean = false,
    )

    sealed class Label {
        object WalletExited: Label()
    }
}