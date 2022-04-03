package com.lindevhard.felia.component.wallet.detail

import com.arkivanov.decompose.value.Value
import com.lindevhard.felia.wallet.main.domain.model.WalletDetailDomain

interface WalletDetail {

    val models: Value<Model>

    fun onReceiveClicked()

    fun onSendClicked()

    fun onBackClicked()

    sealed class Model {
        object Loading: Model()
        data class Loaded(val wallet: WalletDetailDomain): Model()
    }

    sealed class Output {
        class OnReceiveClicked(val walletId: Long): Output()
        class OnSendClicked(val walletId: Long): Output()
        object OnBackClicked: Output()
    }
}