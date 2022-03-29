package com.lindevhard.felia.component.create_wallet

class CreateWalletComponent(
    private val output: (CreateWallet.Output) -> Unit
) : CreateWallet {

    override fun onCreateWalletClicked() {
    }

    override fun onBackPressed() {
        output(CreateWallet.Output.Closed)
    }

}