package com.gexabyte.android.wallet.assets

import com.google.gson.annotations.SerializedName

data class CryptoAssetDTO(
    @SerializedName("decimals")
    val decimals: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("explorer")
    val explorer: String,
    @SerializedName("logoURI")
    val logo: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("network")
    val network: Int,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("website")
    val website: String
)