package com.lindevhard.felia.component.wallet.send

import com.arkivanov.decompose.value.Value
import com.lindevhard.felia.wallet.main.domain.model.Wallet
import com.lindevhard.felia.wallet.main.domain.model.WalletDetailDomain
import kotlinx.coroutines.flow.Flow

interface WalletSend {

    val models: Value<Model>
    val events: Flow<Event>

    fun updateAddress(address: String)

    fun updateAmount(amount: String)

    fun clickConfirmButton()

    fun cancelTransaction()

    fun startTransaction()

    fun completeTransaction()

    fun onBackClicked()

    data class Model(
        val wallet: WalletDetailDomain?,
        val addressInput: String,
        val addressIsValid: Boolean,
        val amountInput: String,
        val amountIsValid: Boolean,
        val isEnableConfirmButton: Boolean,
        val isLoading: Boolean,
        val isShowConfirmDialog: Boolean,
    )

    sealed class Event {
        object TransactionSuccess: Event()
        object TransactionFailed: Event()
    }

    sealed class Output {
        object OnBackClicked: Output()
        object OnSuccessTransaction: Output()
    }
}