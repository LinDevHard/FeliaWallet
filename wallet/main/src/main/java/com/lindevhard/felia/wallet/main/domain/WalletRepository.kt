package com.lindevhard.felia.wallet.main.domain

import com.lindevhard.felia.wallet.main.domain.model.CreateWalletRequest
import com.lindevhard.felia.wallet.main.domain.model.Wallet
import com.lindevhard.felia.wallet.main.domain.model.WalletDetail
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface WalletRepository {

    suspend fun createWallets(newWallets: List<CreateWalletRequest>)

    suspend fun updateWalletBalance(walletId: Long, newBalance: BigDecimal): Boolean

    suspend fun flowOnWallets(): Flow<List<Wallet>>

    suspend fun flowOnWalletDetail(walletId: Long): Flow<WalletDetail>

    suspend fun clearWallets()
}