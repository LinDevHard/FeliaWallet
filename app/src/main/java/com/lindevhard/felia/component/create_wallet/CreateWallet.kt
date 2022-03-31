package com.lindevhard.felia.component.create_wallet

import com.arkivanov.decompose.value.Value


interface CreateWallet {

    val models: Value<Model>

    fun onCreateWalletClicked()

    fun onBackPressed()

    fun onCompleteCreated()

    data class Model(
        val seedPhrase: String,
        val wordList: List<String>,
        val isCreated: Boolean,
    )

    sealed class Output {
        object Closed: Output()
        object Created: Output()
    }
}