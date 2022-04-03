package com.lindevhard.felia.component.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.Router
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.gexabyte.android.wallet.rates.RatesRepository
import com.lindevhard.felia.component.main.FeliaMain.Child
import com.lindevhard.felia.component.main.store.WalletMainStoreProvider
import com.lindevhard.felia.component.wallet.list.WalletList
import com.lindevhard.felia.component.wallet.receive.WalletReceiveComponent
import com.lindevhard.felia.component.wallet.send.WalletSendComponent
import com.lindevhard.felia.wallet.main.domain.WalletRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class FeliaMainComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val walletRepository: WalletRepository,
    private val ratesRepository: RatesRepository,
    private val onWalletExit: () -> Unit,
) : FeliaMain, KoinComponent, ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            WalletMainStoreProvider(
                storeFactory = storeFactory,
                repository = walletRepository,
                ratesRepository = ratesRepository
            ).provide()
        }

    private val router: Router<Configuration, Child> = router(
        initialConfiguration = Configuration.List,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override val routerState: Value<RouterState<*, Child>> = router.state

    @OptIn(ExperimentalCoroutinesApi::class)
    override val events: Flow<FeliaMain.Event> = store.labels.map { labelToEvent(it) }

    override fun exitWallet() = onWalletExit()

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): Child = when (configuration) {
        Configuration.List -> {
            val walletListComponent by inject<WalletList> {
                parametersOf(componentContext, store)
            }
            Child.List(walletListComponent)
        }
        Configuration.Receive -> Child.Receive(WalletReceiveComponent())
        Configuration.Send -> Child.Send(WalletSendComponent())
    }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object List : Configuration()

        @Parcelize
        object Receive : Configuration()

        @Parcelize
        object Send : Configuration()
    }
}
