package com.lindevhard.felia.wallet.main.domain.usecase

import com.gexabyte.android.wallet.core.domain.InitWalletRepository
import com.lindevhard.felia.wallet.main.domain.WalletRepository

class LogoutUseCase(
    private val walletRepository: WalletRepository,
    private val initWalletRepository: InitWalletRepository,
) {

    suspend operator fun invoke() {
        walletRepository.clearWallets()
        initWalletRepository.clearStore()
    }
}