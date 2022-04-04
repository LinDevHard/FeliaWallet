package com.lindevhard.felia.component.wallet.receive

import com.arkivanov.decompose.value.Value

interface WalletReceive {
    val models: Value<Model>

    fun onBackClicked()

    data class Model(
        val symbol: String ,
        val name: String ,
        val address: String,
        val isLoading: Boolean,
    )

    sealed class Output{
        object OnBackClicked: Output()
    }
}