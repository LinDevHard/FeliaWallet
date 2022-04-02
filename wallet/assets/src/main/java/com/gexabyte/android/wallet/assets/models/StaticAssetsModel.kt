package com.gexabyte.android.wallet.assets.models
import com.google.gson.annotations.SerializedName


internal data class StaticAssetsModel(
    @SerializedName("assets")
    val assets: List<Asset>,
    @SerializedName("meta")
    val meta: String,
    @SerializedName("version")
    val version: Int
)

internal data class Asset(
    @SerializedName("decimals")
    val decimals: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("explorer")
    val explorer: String,
    @SerializedName("logoURI")
    val logoURI: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("network")
    val network: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("website")
    val website: String,
    @SerializedName("contractAddress")
    val contractAddress: String?
)