package com.lindevhard.felia.component.import_wallet.di

import com.arkivanov.decompose.ComponentContext
import com.lindevhard.felia.component.import_wallet.ImportWallet
import com.lindevhard.felia.component.import_wallet.ImportWalletComponent
import com.lindevhard.felia.wallet.main.domain.usecase.CreateWalletUseCase
import org.koin.dsl.module

val importWalletModule = module {
    factory<ImportWallet> { (componentContext: ComponentContext, output: (ImportWallet.Output) -> Unit) ->
        ImportWalletComponent(
            componentContext = componentContext,
            storeFactory = get(),
            walletRepository = get(),
            createWalletUseCase = get(),
            output = output,
        )
    }
}