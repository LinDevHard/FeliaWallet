package com.lindevhard.felia.component.create_wallet.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.gexabyte.android.wallet.core.domain.InitWalletRepository
import com.lindevhard.felia.component.create_wallet.store.CreateWalletStore.Intent
import com.lindevhard.felia.component.create_wallet.store.CreateWalletStore.State
import kotlinx.coroutines.launch

class CreateWalletStoreProvider(
    private val storeFactory: StoreFactory,
    private val repository: InitWalletRepository,
) {

    fun provide(): CreateWalletStore =
        object : CreateWalletStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "CreateWalletStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Message {
        class GeneratedSeedPhrase(val seed: String): Message()
        object SuccessCreated: Message()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Message, Nothing>() {
        override fun executeAction(action: Unit, getState: () -> State) {
            generateSeedPhrase()
        }

        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.CreateWallet -> {
                    initWallet(state = getState())
                }
            }

        private fun generateSeedPhrase() {
            scope.launch {
                val newSeed = repository.generateMnemonicPhrase()
                dispatch(Message.GeneratedSeedPhrase(seed = newSeed))
            }
        }

        private fun initWallet(state: State) {
            scope.launch {
                repository.saveWallet(mnemonic = state.seed)
                dispatch(Message.SuccessCreated)
            }
        }
    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                is Message.GeneratedSeedPhrase -> copy(seed = msg.seed)
                Message.SuccessCreated -> copy(isCreated = true)
            }
    }
}