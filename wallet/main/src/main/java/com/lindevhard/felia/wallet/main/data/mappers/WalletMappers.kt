package com.lindevhard.felia.wallet.main.data.mappers

import com.gexabyte.android.wallet.assets.CryptoAsset
import com.gexabyte.android.wallet.rates.CoinMarketData
import com.lindevhard.felia.wallet.main.database.entity.WalletEntity
import com.lindevhard.felia.wallet.main.domain.model.CreateWalletRequest
import com.lindevhard.felia.wallet.main.domain.model.Wallet
import com.lindevhard.felia.wallet.main.domain.model.WalletDetail
import java.math.BigDecimal

fun WalletEntity.mapToWallet(assetData: CryptoAsset, marketData: CoinMarketData?): Wallet {
    return Wallet(
        id = this.id,
        address = this.address,
        name = assetData.name,
        symbol = assetData.symbol,
        logo = assetData.logo,
        decimals = assetData.decimals.toLong(),
        balance = this.balance,
        fiatBalance = this.balance *BigDecimal(marketData?.price ?: 0.0),
        fiatRate = BigDecimal(marketData?.price ?: 0.0),
        coinType = this.coinType,
        contractAddress = assetData.contractAddress,
    )
}

fun WalletEntity.mapToWalletDetail(assetData: CryptoAsset, marketData: CoinMarketData?): WalletDetail {
    return WalletDetail(
        id = this.id,
        address = this.address,
        name = assetData.name,
        symbol = assetData.symbol,
        logo = assetData.logo,
        balance = this.balance,
        fiatBalance = this.balance *BigDecimal(marketData?.price ?: 0.0),
        fiatRate = BigDecimal(marketData?.price ?: 0.0),
        coinType = this.coinType,
        contractAddress = assetData.contractAddress,
        asset = assetData,
        marketData = marketData,
    )
}

fun CreateWalletRequest.toWalletEntity(): WalletEntity {
    return WalletEntity(
        address = address,
        coinType = assets.network,
        symbol = assets.symbol,
        name = assets.name,
        balance = BigDecimal.ZERO,
    )
}
