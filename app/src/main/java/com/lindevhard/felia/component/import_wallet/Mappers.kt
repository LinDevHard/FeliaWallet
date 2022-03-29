package com.lindevhard.felia.component.import_wallet

import com.lindevhard.felia.component.import_wallet.store.ImportWalletStore

val stateToModel: (ImportWalletStore.State) -> ImportWallet.Model =
    {
        ImportWallet.Model(
            seed = it.seed,
            isEnable = it.isCorrect
        )
    }

val labelToEvent: (ImportWalletStore.Label) -> ImportWallet.Event =
    {
        when(it) {
            ImportWalletStore.Label.SeedPhraseInvalid -> ImportWallet.Event.InvalidWallet
        }
    }