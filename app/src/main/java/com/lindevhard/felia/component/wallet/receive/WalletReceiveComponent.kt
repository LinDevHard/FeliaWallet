package com.lindevhard.felia.component.wallet.receive

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.lindevhard.felia.component.wallet.detail.store.WalletDetailStoreProvider
import com.lindevhard.felia.utils.asValue
import com.lindevhard.felia.wallet.main.domain.WalletRepository

class WalletReceiveComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val walletRepository: WalletRepository,
    private val walletId: Long,
    private val output: (WalletReceive.Output) -> Unit
) : WalletReceive, ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            WalletDetailStoreProvider(
                storeFactory = storeFactory,
                walletRepository = walletRepository,
                walletId = walletId
            ).provide()
        }

    override val models: Value<WalletReceive.Model> = store.asValue().map(stateToModel)

    override fun onBackClicked() {
        output(WalletReceive.Output.OnBackClicked)
    }
}