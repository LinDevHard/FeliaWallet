package com.lindevhard.felia.component.create_wallet

interface CreateWallet {

    fun onCreateWalletClicked()

    fun onBackPressed()


    sealed class Output {
        object Closed: Output()
    }
}