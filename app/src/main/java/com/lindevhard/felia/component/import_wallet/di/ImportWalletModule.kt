package com.lindevhard.felia.component.import_wallet.di

import com.arkivanov.decompose.ComponentContext
import com.lindevhard.felia.component.import_wallet.ImportWallet
import com.lindevhard.felia.component.import_wallet.ImportWalletComponent
import org.koin.dsl.module

val importWalletModule = module {
    factory<ImportWallet> { (componentContext: ComponentContext, output: (ImportWallet.Output) -> Unit) ->
        ImportWalletComponent(
            componentContext = componentContext,
            storeFactory = get(),
            walletRepository = get(),
            output = output
        )
    }
}