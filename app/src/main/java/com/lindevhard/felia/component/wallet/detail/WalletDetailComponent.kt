package com.lindevhard.felia.component.wallet.detail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.lindevhard.felia.component.wallet.detail.store.WalletDetailStoreProvider
import com.lindevhard.felia.utils.asValue
import com.lindevhard.felia.wallet.main.domain.WalletRepository

class WalletDetailComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val walletRepository: WalletRepository,
    private val walletId: Long,
    private val output: (WalletDetail.Output) -> Unit
) : WalletDetail, ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            WalletDetailStoreProvider(
                storeFactory = storeFactory,
                walletRepository = walletRepository,
                walletId = walletId
            ).provide()
        }

    override val models: Value<WalletDetail.Model> = store.asValue().map(stateToModel)

    override fun onReceiveClicked() {
        output(WalletDetail.Output.OnReceiveClicked(walletId = walletId))
    }

    override fun onSendClicked() {
        output(WalletDetail.Output.OnSendClicked(walletId = walletId))
    }

    override fun onBackClicked() {
        output(WalletDetail.Output.OnBackClicked)
    }

}