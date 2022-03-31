package com.lindevhard.felia.component.create_wallet

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.gexabyte.android.wallet.core.domain.InitWalletRepository
import com.lindevhard.felia.component.create_wallet.CreateWallet.Model
import com.lindevhard.felia.component.create_wallet.store.CreateWalletStore
import com.lindevhard.felia.component.create_wallet.store.CreateWalletStoreProvider
import com.lindevhard.felia.utils.asValue

class CreateWalletComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    walletRepository: InitWalletRepository,
    private val output: (CreateWallet.Output) -> Unit
) : CreateWallet, ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            CreateWalletStoreProvider(
                storeFactory = storeFactory,
                repository = walletRepository,
            ).provide()
        }

    override val models: Value<Model> = store.asValue().map(stateToModel)

    override fun onCreateWalletClicked() =
        store.accept(CreateWalletStore.Intent.CreateWallet)

    override fun onBackPressed() =
        output(CreateWallet.Output.Closed)

    override fun onCompleteCreated() =
        output(CreateWallet.Output.Created)

}