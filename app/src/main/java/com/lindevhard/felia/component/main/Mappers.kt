package com.lindevhard.felia.component.main

import com.lindevhard.felia.component.main.store.WalletMainStore

val labelToEvent: (WalletMainStore.Label) -> FeliaMain.Event =
    {
        when(it) {
            WalletMainStore.Label.WalletExited -> FeliaMain.Event.WalletExited
        }
    }