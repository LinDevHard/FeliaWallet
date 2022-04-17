package com.lindevhard.felia.component.wallet.send.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.gexabyte.android.wallet.ethereum.domain.EthereumTransferRepository
import com.lindevhard.felia.component.wallet.send.store.WalletSendStore.Intent
import com.lindevhard.felia.component.wallet.send.store.WalletSendStore.Label
import com.lindevhard.felia.component.wallet.send.store.WalletSendStore.State
import com.lindevhard.felia.wallet.main.domain.WalletRepository
import com.lindevhard.felia.wallet.main.domain.model.WalletDetailDomain
import com.lindevhard.felia.wallet.main.domain.usecase.UpdateWalletBalanceUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.math.BigDecimal

class WalletSendStoreProvider(
    private val storeFactory: StoreFactory,
    private val walletRepository: WalletRepository,
    private val ethereumTransferRepository: EthereumTransferRepository,
    private val updateWalletBalanceUseCase: UpdateWalletBalanceUseCase,
    private val walletId: Long,
) {

    fun provide(): WalletSendStore =
        object : WalletSendStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = "WalletSendStore",
                initialState = State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed class Message {
        class WalletLoaded(val wallet: WalletDetailDomain) : Message()
        class UpdateAmount(val amountInput: String, val amount: BigDecimal, val isValid: Boolean) :
            Message()

        class UpdateAddress(val address: String, val isValid: Boolean) : Message()
        class ShowConfirmDialog(val isShow: Boolean) : Message()
        class UpdateTransaction(val isStarted: Boolean) : Message()
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Unit, State, Message, Label>() {

        override fun executeAction(action: Unit, getState: () -> State) {
            scope.launch {
                observeWallet()
            }
        }

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.HideConfirm -> dispatch(Message.ShowConfirmDialog(isShow = false))
                Intent.OnConfirmClick -> dispatch(Message.ShowConfirmDialog(isShow = true))
                Intent.StartTransaction -> runTransaction(getState())
                is Intent.UpdateAddressInput -> updateAddress(intent.address)
                is Intent.UpdateAmountInput -> updateAmount(
                    amountText = intent.amount,
                    state = getState()
                )
            }
        }

        fun runTransaction(state: State) {
            val isAllowTransaction =
                state.addressIsValid && state.amountIsValid && state.wallet != null
            if (!isAllowTransaction) {
                return
            }

            scope.launch {
                state.wallet?.let { walletDomain ->
                    val transaction = if (walletDomain.symbol == "ETH") {
                        ethereumTransferRepository.transferEth(
                            toAddress = state.addressInput,
                            amount = state.amount,
                        )
                    } else {
                        ethereumTransferRepository.transferTokens(
                            toAddress = state.addressInput,
                            amount = state.amount,
                            tokenAddress = walletDomain.contractAddress ?: "",
                            symbol = walletDomain.symbol,
                        )
                    }

                    transaction
                        .onStart {
                            dispatch(Message.UpdateTransaction(isStarted = true))
                        }
                        .catch {
                            dispatch(Message.UpdateTransaction(isStarted = false))
                            publish(Label.TransactionError(it))
                        }.collectLatest {
                            updateWalletBalanceUseCase()
                            dispatch(Message.UpdateTransaction(isStarted = false))
                            publish(Label.TransactionSuccess)
                        }
                }
            }
        }

        fun updateAddress(address: String) {
            val addressIsValid = address.isEmpty() || ethereumTransferRepository
                .validateAddress(address = address)

            dispatch(Message.UpdateAddress(address = address, isValid = addressIsValid))
        }

        fun updateAmount(amountText: String, state: State) {
            val wallet = state.wallet ?: return

            if (amountText.isEmpty()) {
                dispatch(
                    Message.UpdateAmount(
                        amountInput = amountText,
                        amount = BigDecimal.ZERO,
                        isValid = true
                    )
                )
            } else {
                val amount = BigDecimal(amountText) * BigDecimal.TEN.pow(wallet.decimals.toInt())
                val amountIsValid = wallet.balance > amount

                dispatch(
                    Message.UpdateAmount(
                        amountInput = amountText,
                        amount = amount,
                        isValid = amountIsValid
                    )
                )
            }
        }

        suspend fun observeWallet() {
            walletRepository
                .flowOnWalletDetail(walletId = walletId)
                .collectLatest { walletDetail ->
                    dispatch(Message.WalletLoaded(walletDetail))
                }
        }
    }

    private object ReducerImpl :
        Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                is Message.WalletLoaded -> copy(wallet = msg.wallet)
                is Message.UpdateAddress -> copy(
                    addressInput = msg.address,
                    addressIsValid = msg.isValid
                )
                is Message.UpdateAmount -> copy(
                    amount = msg.amount,
                    amountInput = msg.amountInput,
                    amountIsValid = msg.isValid
                )
                is Message.ShowConfirmDialog -> copy(isShowConfirmDialog = msg.isShow)
                is Message.UpdateTransaction -> copy(
                    isLoading = msg.isStarted,
                    isShowConfirmDialog = false
                )
            }
    }
}