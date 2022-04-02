package com.lindevhard.felia.wallet.main.data

import com.gexabyte.android.wallet.assets.AssetsRepository
import com.gexabyte.android.wallet.rates.RatesRepository
import com.lindevhard.felia.wallet.main.data.mappers.mapToWallet
import com.lindevhard.felia.wallet.main.data.mappers.mapToWalletDetail
import com.lindevhard.felia.wallet.main.data.mappers.toWalletEntity
import com.lindevhard.felia.wallet.main.database.dao.WalletDao
import com.lindevhard.felia.wallet.main.domain.WalletRepository
import com.lindevhard.felia.wallet.main.domain.model.CreateWalletRequest
import com.lindevhard.felia.wallet.main.domain.model.Wallet
import com.lindevhard.felia.wallet.main.domain.model.WalletDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import java.math.BigDecimal

class WalletRepositoryImpl(
    private val assetsRepository: AssetsRepository,
    private val ratesRepository: RatesRepository,
    private val walletDao: WalletDao,
) : WalletRepository {
    override suspend fun createWallets(newWallets: List<CreateWalletRequest>) {
        walletDao.deleteAll()
        walletDao.insertWallets(
            newWallets.map { request ->
                request.toWalletEntity()
            }
        )
    }

    override suspend fun updateWalletBalance(walletId: Long, newBalance: BigDecimal): Boolean {
        val walletEntity = walletDao.findWalletById(walletId) ?: return false

        return if (newBalance != walletEntity.balance) {
            walletDao.updateWallet(walletEntity.copy(balance = newBalance))
            true
        } else {
            false
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun flowOnWallets(): Flow<List<Wallet>> = walletDao
        .flowOnWallets()
        .mapLatest { walletList ->
            walletList.map { walletEntity ->
                val asset = assetsRepository.getAsset(walletEntity.symbol, walletEntity.coinType)
                val cmcData = ratesRepository.getCmcPrice(walletEntity.symbol)

                walletEntity.mapToWallet(asset, cmcData)
            }
        }.flowOn(Dispatchers.IO)

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun flowOnWalletDetail(walletId: Long): Flow<WalletDetail> {
        val walletEntity = walletDao.findWalletById(walletId) ?: return flow { }
        val asset = assetsRepository.getAsset(walletEntity.symbol, walletEntity.coinType)
        return combine(
            walletDao.flowOnWalletById(walletId),
            ratesRepository.flowOnCmcPrice(asset.symbol)
        ) { wallet, coinMarketData ->
            wallet.mapToWalletDetail(asset, coinMarketData)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun clearWallets() {
        walletDao.deleteAll()
    }

}