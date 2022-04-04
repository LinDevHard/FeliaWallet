package com.lindevhard.felia.component.root.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.gexabyte.android.wallet.assets.di.assetsModule
import com.gexabyte.android.wallet.core.di.walletCoreModule
import com.gexabyte.android.wallet.ethereum.di.EthereumModuleDependencies
import com.gexabyte.android.wallet.ethereum.di.ethereumModule
import com.gexabyte.android.wallet.rates.di.cmcModule
import com.lindevhard.felia.component.auth.di.authFlowModule
import com.lindevhard.felia.component.create_wallet.di.createWalletModule
import com.lindevhard.felia.component.import_wallet.di.importWalletModule
import com.lindevhard.felia.component.main.di.mainComponentModule
import com.lindevhard.felia.component.root.FeliaRoot
import com.lindevhard.felia.component.root.FeliaRootComponent
import com.lindevhard.felia.component.wallet.detail.di.walletDetailComponentModule
import com.lindevhard.felia.component.wallet.list.di.walletListComponentModule
import com.lindevhard.felia.component.wallet.receive.di.walletReceiveComponentModule
import com.lindevhard.felia.wallet.main.di.walletMainModule
import okhttp3.OkHttpClient
import org.koin.dsl.module
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService

val rootModule = module {

    single<StoreFactory> { DefaultStoreFactory() }
    single<EthereumModuleDependencies> { EthereumDep }

    factory<FeliaRoot> { (componentContext: ComponentContext) ->
        FeliaRootComponent(componentContext = componentContext, walletRepository = get())
    }
}

val featureModules = listOf(
    authFlowModule,
    importWalletModule,
    createWalletModule,
    mainComponentModule,
    walletListComponentModule,
    walletDetailComponentModule,
    walletReceiveComponentModule,
)

val walletModules = listOf(
    walletCoreModule,
    walletMainModule,
    assetsModule,
    cmcModule,
    ethereumModule,
)

object EthereumDep : EthereumModuleDependencies {
    override fun provideWeb3j(): Web3j {
        return Web3j.build(
            HttpService(
                "https://kovan.infura.io/v3/589795988522468cad5dba9c827e948b",
                OkHttpClient()
            )
        )
    }
}
