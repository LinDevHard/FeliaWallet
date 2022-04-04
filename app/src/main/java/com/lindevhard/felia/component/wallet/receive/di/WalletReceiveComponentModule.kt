package com.lindevhard.felia.component.wallet.receive.di

import com.arkivanov.decompose.ComponentContext
import com.lindevhard.felia.component.wallet.receive.WalletReceive
import com.lindevhard.felia.component.wallet.receive.WalletReceiveComponent
import org.koin.dsl.module

val walletReceiveComponentModule = module {

    factory<WalletReceive> { (componentContext: ComponentContext, walletId: Long,  output: (WalletReceive.Output) -> Unit) ->
        WalletReceiveComponent(
            componentContext = componentContext,
            storeFactory = get(),
            walletRepository = get(),
            walletId = walletId,
            output = output,
        )
    }
}