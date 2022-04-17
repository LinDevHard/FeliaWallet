package com.lindevhard.felia.component.wallet.send.store

import com.arkivanov.mvikotlin.core.store.Store
import com.lindevhard.felia.component.wallet.send.store.WalletSendStore.Intent
import com.lindevhard.felia.component.wallet.send.store.WalletSendStore.State
import com.lindevhard.felia.component.wallet.send.store.WalletSendStore.Label
import com.lindevhard.felia.wallet.main.domain.model.Wallet
import com.lindevhard.felia.wallet.main.domain.model.WalletDetailDomain
import java.math.BigDecimal

interface WalletSendStore: Store<Intent, State, Label> {

    sealed class Intent {
        class UpdateAddressInput(val address: String): Intent()
        class UpdateAmountInput(val amount: String): Intent()
        object OnConfirmClick: Intent()
        object HideConfirm: Intent()
        object StartTransaction: Intent()
    }

    data class State(
        val wallet: WalletDetailDomain? = null,
        val addressInput: String = "",
        val addressIsValid: Boolean = true,
        val amountInput: String = "",
        val amountIsValid: Boolean = true,
        val amount: BigDecimal = BigDecimal.ZERO,
        val isLoading: Boolean = false,
        val isShowConfirmDialog: Boolean = false,
    )

    sealed class Label {
        object TransactionSuccess : Label()
        class TransactionError(val exception: Throwable): Label()
    }
}