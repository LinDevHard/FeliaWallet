package com.lindevhard.felia.wallet.main.domain.usecase

import android.util.Log
import com.gexabyte.android.wallet.ethereum.domain.EthereumBalanceRepository
import com.lindevhard.felia.wallet.main.domain.WalletRepository
import kotlinx.coroutines.flow.first

class UpdateWalletBalanceUseCase(
    private val ethereumBalanceRepository: EthereumBalanceRepository,
    private val walletRepository: WalletRepository
) {

    suspend operator fun invoke() {
        val walletList = walletRepository.flowOnWallets().first()

        walletList.forEach { wallet ->
            if (wallet.symbol == "ETH") {
                val balance = ethereumBalanceRepository.getEthBalance(wallet.address).first()
                Log.d("Sd", "Balance ${wallet.symbol}: $balance")

                walletRepository.updateWalletBalance(
                    walletId = wallet.id,
                    newBalance = balance.toBigDecimal()
                )
            } else {
                val balance = ethereumBalanceRepository.getTokenBalance(
                    symbol = wallet.symbol,
                    tokenAddress = wallet.contractAddress ?: "",
                    address = wallet.address
                ).first()
                Log.d("Sd", "Balance ${wallet.symbol}: $balance")

                walletRepository.updateWalletBalance(
                    walletId = wallet.id,
                    newBalance = balance.toBigDecimal()
                )
            }
        }
    }

    suspend operator fun invoke(walletIds: List<Long>) {
        val walletList = walletRepository.flowOnWallets().first()

        walletList.forEach { wallet ->
            if (wallet.id in walletIds) {
                if (wallet.symbol == "ETH") {
                    val balance = ethereumBalanceRepository.getEthBalance(wallet.address).first()
                    Log.d("Sd", "Balance ${wallet.symbol}: $balance")

                    walletRepository.updateWalletBalance(
                        walletId = wallet.id,
                        newBalance = balance.toBigDecimal()
                    )
                } else {
                    val balance = ethereumBalanceRepository.getTokenBalance(
                        symbol = wallet.symbol,
                        tokenAddress = wallet.contractAddress ?: "",
                        address = wallet.address
                    ).first()
                    Log.d("Sd", "Balance ${wallet.symbol}: $balance")

                    walletRepository.updateWalletBalance(
                        walletId = wallet.id,
                        newBalance = balance.toBigDecimal()
                    )
                }
            }
        }
    }
}