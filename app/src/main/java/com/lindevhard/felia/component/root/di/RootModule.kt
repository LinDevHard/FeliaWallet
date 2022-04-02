package com.lindevhard.felia.component.root.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.gexabyte.android.wallet.assets.di.assetsModule
import com.gexabyte.android.wallet.core.di.walletCoreModule
import com.gexabyte.android.wallet.rates.di.cmcModule
import com.lindevhard.felia.component.auth.di.authFlowModule
import com.lindevhard.felia.component.create_wallet.di.createWalletModule
import com.lindevhard.felia.component.import_wallet.di.importWalletModule
import com.lindevhard.felia.component.root.FeliaRoot
import com.lindevhard.felia.component.root.FeliaRootComponent
import com.lindevhard.felia.wallet.main.di.walletMainModule
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
    createWalletModule,
)

val walletModules = listOf(
    walletCoreModule,
    walletMainModule,
    assetsModule,
    cmcModule,
)