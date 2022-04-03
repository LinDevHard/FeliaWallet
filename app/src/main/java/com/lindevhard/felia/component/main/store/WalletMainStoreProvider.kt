package com.lindevhard.felia.component.main.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.gexabyte.android.wallet.rates.RatesRepository
import com.lindevhard.felia.component.main.store.WalletMainStore.Intent
import com.lindevhard.felia.component.main.store.WalletMainStore.Label
import com.lindevhard.felia.component.main.store.WalletMainStore.State
import com.lindevhard.felia.wallet.main.domain.WalletRepository
import com.lindevhard.felia.wallet.main.domain.model.Wallet
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class WalletMainStoreProvider(
    private val storeFactory: StoreFactory,
    private val repository: WalletRepository,
    private val ratesRepository: RatesRepository,
) {

    fun provide(): WalletMainStore =
        object : WalletMainStore, Store<Intent, State, Label> by storeFactory.create(
            name = "WalletMainStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Message {
        class WalletUpdated(val wallets: List<Wallet>) : Message()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Message, Label>() {
        override fun executeAction(action: Unit, getState: () -> State) {

            scope.launch {
                ratesRepository.updateAllCmcData()
                observeWallets()
            }
        }

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.RefreshWallet -> TODO()
                is Intent.RefreshWalletById -> TODO()
                Intent.WalletExit -> scope.launch { exitWallet() }
            }
        }

        suspend fun observeWallets() {
            repository.flowOnWallets().collectLatest {
                dispatch(Message.WalletUpdated(it))
            }
        }

        suspend fun exitWallet() {
            repository.clearWallets()
            publish(Label.WalletExited)
        }
    }

    private object ReducerImpl :
        Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                is Message.WalletUpdated -> copy(isLoading = false, wallets = msg.wallets)
            }
    }
}