package com.lindevhard.felia.component.import_wallet.store

import com.arkivanov.mvikotlin.core.store.Store

import com.lindevhard.felia.component.import_wallet.store.ImportWalletStore.Label
import com.lindevhard.felia.component.import_wallet.store.ImportWalletStore.State
import com.lindevhard.felia.component.import_wallet.store.ImportWalletStore.Intent

interface ImportWalletStore: Store<Intent, State, Label> {

    sealed class Intent {
        data class SetText(val text: String) : Intent()
        object ImportWallet: Intent()
    }

    data class State(
        val seed: String = "",
        val isCorrect: Boolean = false
    )

    sealed class Label {
        object SeedPhraseInvalid: Label()
    }
}