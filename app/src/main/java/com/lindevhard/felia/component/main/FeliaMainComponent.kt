package com.lindevhard.felia.component.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.Router
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.pop
import com.arkivanov.decompose.router.push
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.gexabyte.android.wallet.rates.RatesRepository
import com.lindevhard.felia.component.main.FeliaMain.Child
import com.lindevhard.felia.component.main.store.WalletMainStoreProvider
import com.lindevhard.felia.component.wallet.detail.WalletDetail
import com.lindevhard.felia.component.wallet.list.WalletList
import com.lindevhard.felia.component.wallet.receive.WalletReceive
import com.lindevhard.felia.component.wallet.send.WalletSendComponent
import com.lindevhard.felia.wallet.main.domain.WalletRepository
import com.lindevhard.felia.wallet.main.domain.usecase.LogoutUseCase
import com.lindevhard.felia.wallet.main.domain.usecase.UpdateWalletBalanceUseCase
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
    private val updateWalletBalanceUseCase: UpdateWalletBalanceUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val onWalletExit: () -> Unit,
) : FeliaMain, KoinComponent, ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            WalletMainStoreProvider(
                storeFactory = storeFactory,
                repository = walletRepository,
                ratesRepository = ratesRepository,
                updateWalletUseCase = updateWalletBalanceUseCase,
                logoutUseCase = logoutUseCase,
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
                parametersOf(componentContext, store, ::onWalletListOutput)
            }
            Child.List(walletListComponent)
        }
        is Configuration.Receive ->{
            val walletReceiveComponent by inject<WalletReceive> {
                parametersOf(componentContext, configuration.walletId, ::onWalletReceiveOutput)
            }

            Child.Receive(walletReceiveComponent)
        }
        is Configuration.Send -> Child.Send(WalletSendComponent())
        is Configuration.Detail -> {
            val walletDetailComponent by inject<WalletDetail> {
                parametersOf(componentContext, configuration.walletId, ::onWalletDetailOutput)
            }

            Child.Detail(walletDetailComponent)
        }
    }

    private fun onWalletListOutput(output: WalletList.Output) {
        when(output) {
            is WalletList.Output.OnWalletClicked ->
                router.push(Configuration.Detail(output.walletId))
        }
    }

    private fun onWalletDetailOutput(output: WalletDetail.Output) {
        when(output) {
            is WalletDetail.Output.OnReceiveClicked ->
                router.push(Configuration.Receive(output.walletId))
            is WalletDetail.Output.OnSendClicked ->
                router.push(Configuration.Send(output.walletId))
            WalletDetail.Output.OnBackClicked ->
                router.pop()
        }
    }

    private fun onWalletReceiveOutput(output: WalletReceive.Output) {
        when(output) {
            WalletReceive.Output.OnBackClicked -> router.pop()
        }
    }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object List : Configuration()

        @Parcelize
        class Receive(val walletId: Long) : Configuration()

        @Parcelize
        class Send(val walletId: Long): Configuration()

        @Parcelize
        class Detail(val walletId: Long): Configuration()
    }
}
