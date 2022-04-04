package com.lindevhard.felia.component.wallet.receive

import com.arkivanov.decompose.value.Value
import com.lindevhard.felia.wallet.main.domain.model.WalletDetailDomain

interface WalletReceive {
    val models: Value<Model>

    fun onBackClicked()

    data class Model(
        val wallet: WalletDetailDomain? = null,
        val isLoading: Boolean,
    )

    sealed class Output{
        object OnBackClicked: Output()
    }
}