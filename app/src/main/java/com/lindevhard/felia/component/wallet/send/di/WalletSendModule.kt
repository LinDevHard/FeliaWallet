package com.lindevhard.felia.component.wallet.send.di

import com.arkivanov.decompose.ComponentContext
import com.lindevhard.felia.component.wallet.send.WalletSend
import com.lindevhard.felia.component.wallet.send.WalletSendComponent
import org.koin.dsl.module

val walletSendModule = module {
    factory<WalletSend> { (componentContext: ComponentContext, walletId: Long,  output: (WalletSend.Output) -> Unit) ->
        WalletSendComponent(
            componentContext = componentContext,
            storeFactory = get(),
            walletRepository = get(),
            updateWalletBalanceUseCase = get(),
            transferRepository = get(),
            output = output,
            walletId = walletId,
        )
    }
}