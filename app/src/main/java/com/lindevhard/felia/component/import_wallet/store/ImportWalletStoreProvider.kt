package com.lindevhard.felia.component.import_wallet.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.gexabyte.android.wallet.core.domain.InitWalletRepository
import com.lindevhard.felia.component.import_wallet.store.ImportWalletStore.Intent
import com.lindevhard.felia.component.import_wallet.store.ImportWalletStore.Label
import com.lindevhard.felia.component.import_wallet.store.ImportWalletStore.State
import kotlinx.coroutines.launch

class ImportWalletStoreProvider(
    private val storeFactory: StoreFactory,
    private val repository: InitWalletRepository,
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
        object WrongSeed: Result()
        object SuccessImport: Result()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Result, Label>() {

        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.SetText -> setText(text = intent.text)
                Intent.ImportWallet -> {
                    initWallet(state = getState())
                }
            }

        private fun setText(text: String) {
            dispatch(Result.SeedChanged(seed = text))
        }

        private fun initWallet(state: State) {
            scope.launch {
                if (repository.validateMnemonic(state.seed)) {
                    val wallet = repository.initWalletFromMnemonicPhrase(state.seed)
                    repository.saveWallet(mnemonic = wallet.mnemonic())
                    dispatch(Result.SuccessImport)
                } else {
                    dispatch(Result.WrongSeed)
                    publish(Label.SeedPhraseInvalid)
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Result> {
        override fun State.reduce(msg: Result): State =
            when (msg) {
                is Result.SeedChanged -> copy(seed = msg.seed, isError = false)
                Result.SuccessImport -> copy(isError = false, isImported = true)
                Result.WrongSeed -> copy(isError = true, isImported = false)
            }
    }
}