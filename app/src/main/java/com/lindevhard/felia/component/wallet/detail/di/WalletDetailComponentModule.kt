package com.lindevhard.felia.component.wallet.detail.di

import com.arkivanov.decompose.ComponentContext
import com.lindevhard.felia.component.wallet.detail.WalletDetail
import com.lindevhard.felia.component.wallet.detail.WalletDetailComponent
import org.koin.dsl.module

val walletDetailComponentModule = module {
    factory<WalletDetail> { (componentContext: ComponentContext, walletId: Long,  output: (WalletDetail.Output) -> Unit) ->
        WalletDetailComponent(
            componentContext = componentContext,
            storeFactory = get(),
            walletRepository = get(),
            walletId = walletId,
            output = output,
        )
    }
}