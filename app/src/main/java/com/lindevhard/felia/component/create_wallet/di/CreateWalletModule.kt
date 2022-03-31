package com.lindevhard.felia.component.create_wallet.di

import com.arkivanov.decompose.ComponentContext
import com.lindevhard.felia.component.create_wallet.CreateWallet
import com.lindevhard.felia.component.create_wallet.CreateWalletComponent
import org.koin.dsl.module

val createWalletModule = module {
    factory<CreateWallet> { (componentContext: ComponentContext, output: (CreateWallet.Output) -> Unit) ->
        CreateWalletComponent(
            componentContext = componentContext,
            storeFactory = get(),
            walletRepository = get(),
            output = output
        )
    }
}