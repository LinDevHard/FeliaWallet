package com.gexabyte.android.wallet.assets.mapper

import com.gexabyte.android.wallet.assets.CryptoAsset
import com.gexabyte.android.wallet.assets.database.entity.CryptoAssetEntity
import com.gexabyte.android.wallet.assets.models.Asset
import wallet.core.jni.CoinType
import java.util.Locale

internal fun CryptoAssetEntity.toModel(): CryptoAsset {
    return CryptoAsset(
        name = this.name,
        symbol = this.symbol,
        description = this.description,
        decimals = this.decimals,
        website = this.website,
        type = this.type,
        explorer = this.explorer,
        network = this.network,
        logo = this.logo,
        contractAddress = contractAddress
    )
}

internal fun Asset.toCryptoAssetsEntity(): CryptoAssetEntity {
    return CryptoAssetEntity(
        name = this.name,
        symbol = this.symbol,
        description = this.description,
        decimals = this.decimals,
        website = this.website,
        type = this.type,
        explorer = this.explorer,
        network = this.network.toCoinType(),
        logo = this.logoURI,
        contractAddress = this.contractAddress,
    )
}

internal fun String.toCoinType(): Int {
    return when(this.lowercase(Locale.ROOT)) {
        "bitcoin" -> CoinType.BITCOIN.value()
        "binance" -> CoinType.BINANCE.value()
        "ethereum" -> CoinType.ETHEREUM.value()
        "tron" -> CoinType.TRON.value()
        "stellar" -> CoinType.STELLAR.value()
        "polkadot" -> CoinType.POLKADOT.value()
        "cardano" -> CoinType.CARDANO.value()
        else ->
            throw IllegalArgumentException(
                "Invalid network type in coins.json file. Please check this file!"
            )
    }
}
