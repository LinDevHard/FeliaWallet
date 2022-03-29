package com.lindevhard.felia.component.import_wallet.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor

import com.lindevhard.felia.component.import_wallet.store.ImportWalletStore.Label
import com.lindevhard.felia.component.import_wallet.store.ImportWalletStore.State
import com.lindevhard.felia.component.import_wallet.store.ImportWalletStore.Intent

class ImportWalletStoreProvider(
    private val storeFactory: StoreFactory,
) {

    fun provide(): ImportWalletStore =
        object : ImportWalletStore, Store<Intent, State, Label> by storeFactory.create(
            name = "ImportWalletStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Result {
        data class SeedChanged(val seed: String) : Result()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Result, Label>() {
        override fun executeAction(action: Unit, getState: () -> State) {

        }

        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.SetText -> setText(text = intent.text, state = getState())
                Intent.ImportWallet ->  {}
            }

        private fun setText(text: String, state: State) {
            dispatch(Result.SeedChanged(seed = text))
            publish(Label.SeedPhraseInvalid)
        }
    }

    private object ReducerImpl : Reducer<State, Result> {
        override fun State.reduce(msg: Result): State =
            when (msg) {
                is Result.SeedChanged -> copy(seed = msg.seed)
            }
    }
}