package com.gexabyte.android.wallet.assets


data class CryptoAsset(
    val decimals: Int,
    val description: String,
    val explorer: String,
    val logo: String,
    val name: String,
    val network: Int,
    val symbol: String,
    val type: String,
    val website: String,
    val contractAddress: String?,
)