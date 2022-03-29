package com.lindevhard.felia.component.import_wallet

import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.Flow


interface ImportWallet {

    val models: Value<Model>

    val events: Flow<Event>

    fun onTextChanged(text: String)

    fun onBackPressed()

    fun onImportWalletClicked()

    data class Model(
        val seed: String,
        val isEnable: Boolean,
    )

    sealed class Output {
        object Closed: Output()
    }

    sealed class Event {
        object InvalidWallet: Event()
    }
}