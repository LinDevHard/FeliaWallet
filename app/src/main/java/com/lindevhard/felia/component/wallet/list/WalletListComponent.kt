package com.lindevhard.felia.component.wallet.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.lindevhard.felia.component.main.store.WalletMainStore
import com.lindevhard.felia.component.main.store.WalletMainStoreProvider
import com.lindevhard.felia.utils.asValue
import com.lindevhard.felia.wallet.main.domain.WalletRepository


class WalletListComponent(
    componentContext: ComponentContext,
    private val walletMainStore: WalletMainStore,
    private val output: (WalletList.Output) -> Unit
) : WalletList, ComponentContext by componentContext {

    override val models: Value<WalletList.Model> = walletMainStore.asValue().map(stateToModel)

    override fun exitWallet() =
        walletMainStore.accept(WalletMainStore.Intent.WalletExit)

    override fun onWalletClick(walletId: Long) =
        output(WalletList.Output.OnWalletClicked(walletId))

}