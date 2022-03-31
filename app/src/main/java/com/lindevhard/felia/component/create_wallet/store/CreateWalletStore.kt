package com.lindevhard.felia.component.create_wallet.store

import com.arkivanov.mvikotlin.core.store.Store
import com.lindevhard.felia.component.create_wallet.store.CreateWalletStore.Intent
import com.lindevhard.felia.component.create_wallet.store.CreateWalletStore.State

interface CreateWalletStore: Store<Intent, State, Nothing> {

    sealed class Intent {
        object CreateWallet : Intent()
    }

    data class State(
        val seed: String = "",
        val isCreated: Boolean = false,
    )
}