package com.lindevhard.felia.component.main.di

import com.arkivanov.decompose.ComponentContext
import com.lindevhard.felia.component.main.FeliaMain
import com.lindevhard.felia.component.main.FeliaMainComponent
import org.koin.dsl.module

val mainComponentModule = module {
    factory<FeliaMain> { (componentContext: ComponentContext, onWalletExit: () -> Unit) ->
        FeliaMainComponent(
            componentContext = componentContext,
            walletRepository = get(),
            storeFactory = get(),
            ratesRepository = get(),
            updateWalletBalanceUseCase = get(),
            logoutUseCase = get(),
            onWalletExit = onWalletExit
        )
    }
}