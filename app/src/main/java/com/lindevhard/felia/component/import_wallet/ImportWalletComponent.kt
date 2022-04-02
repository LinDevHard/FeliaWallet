package com.lindevhard.felia.component.import_wallet

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.gexabyte.android.wallet.core.domain.InitWalletRepository
import com.lindevhard.felia.component.import_wallet.store.ImportWalletStore
import com.lindevhard.felia.component.import_wallet.store.ImportWalletStoreProvider
import com.lindevhard.felia.utils.asValue
import com.lindevhard.felia.wallet.main.domain.usecase.CreateWalletUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ImportWalletComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    walletRepository: InitWalletRepository,
    createWalletUseCase: CreateWalletUseCase,
    private val output: (ImportWallet.Output) -> Unit
) : ImportWallet, ComponentContext by componentContext{

    private val store =
        instanceKeeper.getStore {
            ImportWalletStoreProvider(
                storeFactory = storeFactory,
                repository = walletRepository,
                createWalletUseCase = createWalletUseCase,
            ).provide()
        }

    override val models: Value<ImportWallet.Model> = store.asValue().map(stateToModel)
    override val events: Flow<ImportWallet.Event> = store.labels.map { labelToEvent(it) }

    override fun onTextChanged(text: String) =
        store.accept(ImportWalletStore.Intent.SetText(text))

    override fun onBackPressed() =
        output(ImportWallet.Output.Closed)

    override fun onImportWalletClicked() =
        store.accept(ImportWalletStore.Intent.ImportWallet)

    override fun onCompleteImport() =
        output(ImportWallet.Output.Imported)

}