package com.lindevhard.felia.component.root.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.lindevhard.felia.component.auth.di.authFlowModule
import com.lindevhard.felia.component.import_wallet.di.importWalletModule
import com.lindevhard.felia.component.root.FeliaRoot
import com.lindevhard.felia.component.root.FeliaRootComponent
import org.koin.dsl.module

val rootModule = module {

    single<StoreFactory> { DefaultStoreFactory() }


    factory<FeliaRoot> { (componentContext: ComponentContext) ->
        FeliaRootComponent(componentContext = componentContext, walletRepository = get())
    }
}

val featureModules = listOf(
    authFlowModule,
    importWalletModule,
)