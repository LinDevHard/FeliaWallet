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
    walletMainStore: WalletMainStore,
) : WalletList, ComponentContext by componentContext {

    override val models: Value<WalletList.Model> = walletMainStore.asValue().map(stateToModel)

}