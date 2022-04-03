package com.lindevhard.felia.component.wallet.list.di

import com.arkivanov.decompose.ComponentContext
import com.lindevhard.felia.component.main.store.WalletMainStore
import com.lindevhard.felia.component.wallet.list.WalletList
import com.lindevhard.felia.component.wallet.list.WalletListComponent
import org.koin.dsl.module

val walletListComponentModule = module {
    factory<WalletList> { (componentContext: ComponentContext, walletMainStore: WalletMainStore) ->
        WalletListComponent(
            componentContext = componentContext,
            walletMainStore = walletMainStore
        )
    }
}