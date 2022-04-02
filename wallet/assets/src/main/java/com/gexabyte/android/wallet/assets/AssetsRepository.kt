package com.gexabyte.android.wallet.assets

/**
 * Public api for assets
 */
interface AssetsRepository {

    suspend fun getAsset(symbol: String, coinType: Int? = null): CryptoAsset

    suspend fun getAssetList(): List<CryptoAsset>
}