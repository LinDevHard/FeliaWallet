package com.lindevhard.felia.component.start

class FeliaStartComponent(
    private val onCreateWallet: () -> Unit,
    private val onImportWallet: () -> Unit
): FeliaStart {
    override fun onCreateWalletClicked() = onCreateWallet()

    override fun onImportWalletClicked() = onImportWallet()

}