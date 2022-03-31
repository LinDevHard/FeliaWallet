package com.lindevhard.felia.component.import_wallet

import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.Flow


interface ImportWallet {

    val models: Value<Model>

    val events: Flow<Event>

    fun onTextChanged(text: String)

    fun onBackPressed()

    fun onImportWalletClicked()

    fun onCompleteImport()

    data class Model(
        val seed: String,
        val isError: Boolean,
        val isImported: Boolean,
    )

    sealed class Output {
        object Closed: Output()
        object Imported: Output()
    }

    sealed class Event {
        object InvalidWallet: Event()
    }
}