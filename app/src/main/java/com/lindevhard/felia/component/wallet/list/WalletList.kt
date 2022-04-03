package com.lindevhard.felia.component.wallet.list

import com.arkivanov.decompose.value.Value
import com.lindevhard.felia.wallet.main.domain.model.Wallet

interface WalletList {

    val models: Value<Model>

    data class Model(
        val walletList: List<Wallet>,
        val isLoading: Boolean
    )
}