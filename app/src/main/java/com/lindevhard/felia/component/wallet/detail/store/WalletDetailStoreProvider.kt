package com.lindevhard.felia.component.wallet.detail.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.lindevhard.felia.wallet.main.domain.WalletRepository
import com.lindevhard.felia.wallet.main.domain.model.WalletDetailDomain
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class WalletDetailStoreProvider(
    private val storeFactory: StoreFactory,
    private val walletRepository: WalletRepository,
    private val walletId: Long,
) {

    fun provide(): WalletDetailStore =
        object : WalletDetailStore,
            Store<Nothing, WalletDetailStore.State, Nothing> by storeFactory.create(
                name = "WalletDetailStore",
                initialState = WalletDetailStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed class Message {
        class WalletLoaded(val wallet: WalletDetailDomain) : Message()
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<Nothing, Unit, WalletDetailStore.State, Message, Nothing>() {
        override fun executeAction(action: Unit, getState: () -> WalletDetailStore.State) {
            scope.launch {
                observeWallet()
            }
        }

        suspend fun observeWallet() {
            walletRepository
                .flowOnWalletDetail(walletId = walletId)
                .collectLatest { walletDetail ->
                    Timber.d("walletDetail: $walletDetail")
                    dispatch(Message.WalletLoaded(walletDetail))
                }
        }
    }

    private object ReducerImpl :
        Reducer<WalletDetailStore.State, Message> {
        override fun WalletDetailStore.State.reduce(msg: Message): WalletDetailStore.State =
            when (msg) {
                is Message.WalletLoaded -> copy(walletDetail = msg.wallet)
            }
    }
}