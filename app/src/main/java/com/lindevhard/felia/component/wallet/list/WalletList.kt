package com.lindevhard.felia.component.wallet.list

import com.arkivanov.decompose.value.Value
import com.lindevhard.felia.wallet.main.domain.model.Wallet

interface WalletList {

    val models: Value<Model>

    fun exitWallet()

    fun onWalletClick(walletId: Long)

    data class Model(
        val walletList: List<Wallet>,
        val isLoading: Boolean
    )

    sealed class Output {
        class OnWalletClicked(val walletId: Long): Output()
    }
}