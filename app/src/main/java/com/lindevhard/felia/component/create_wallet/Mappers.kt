package com.lindevhard.felia.component.create_wallet

import com.lindevhard.felia.component.create_wallet.store.CreateWalletStore

val stateToModel: (CreateWalletStore.State) -> CreateWallet.Model =
    {
        CreateWallet.Model(
            seedPhrase = it.seed,
            wordList = it.seed.split(" "),
            isCreated = it.isCreated,
        )
    }