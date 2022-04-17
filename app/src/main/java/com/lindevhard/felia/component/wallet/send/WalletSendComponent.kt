@file:OptIn(ExperimentalCoroutinesApi::class)

package com.lindevhard.felia.component.wallet.send

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.gexabyte.android.wallet.ethereum.domain.EthereumTransferRepository
import com.lindevhard.felia.component.wallet.send.store.WalletSendStore
import com.lindevhard.felia.component.wallet.send.store.WalletSendStoreProvider
import com.lindevhard.felia.utils.asValue
import com.lindevhard.felia.wallet.main.domain.WalletRepository
import com.lindevhard.felia.wallet.main.domain.usecase.UpdateWalletBalanceUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WalletSendComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    walletRepository: WalletRepository,
    transferRepository: EthereumTransferRepository,
    updateWalletBalanceUseCase: UpdateWalletBalanceUseCase,
    walletId: Long,
    private val output: (WalletSend.Output) -> Unit
) : WalletSend, ComponentContext by componentContext {


    private val store =
        instanceKeeper.getStore {
            WalletSendStoreProvider(
                storeFactory = storeFactory,
                walletRepository = walletRepository,
                walletId = walletId,
                ethereumTransferRepository = transferRepository,
                updateWalletBalanceUseCase = updateWalletBalanceUseCase,
            ).provide()
        }

    override val models: Value<WalletSend.Model> = store.asValue().map(stateToModel)

    override val events: Flow<WalletSend.Event> = store.labels.map(transform = labelToEvent)

    override fun updateAddress(address: String) {
        store.accept(WalletSendStore.Intent.UpdateAddressInput(address))
    }

    override fun updateAmount(amount: String) {
        store.accept(WalletSendStore.Intent.UpdateAmountInput(amount))
    }

    override fun clickConfirmButton() {
        store.accept(WalletSendStore.Intent.OnConfirmClick)
    }

    override fun cancelTransaction() {
        store.accept(WalletSendStore.Intent.HideConfirm)
    }

    override fun startTransaction() {
        store.accept(WalletSendStore.Intent.StartTransaction)
    }

    override fun completeTransaction() {
        output(WalletSend.Output.OnSuccessTransaction)
    }

    override fun onBackClicked() {
        output(WalletSend.Output.OnBackClicked)
    }
}