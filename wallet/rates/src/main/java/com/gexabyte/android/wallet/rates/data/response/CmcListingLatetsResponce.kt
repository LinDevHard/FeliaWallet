package com.gexabyte.android.wallet.rates.data.response

import com.google.gson.annotations.SerializedName

internal data class CmcListingLatestResponse(
    @SerializedName("data")
    val data: List<ListingData>,
    @SerializedName("status")
    val status: Status
)

internal data class ListingData(
    @SerializedName("circulating_supply")
    val circulatingSupply: Double,
    @SerializedName("cmc_rank")
    val cmcRank: Int,
    @SerializedName("date_added")
    val dateAdded: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("max_supply")
    val maxSupply: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("num_market_pairs")
    val numMarketPairs: Int,
    @SerializedName("platform")
    val platform: Platform,
    @SerializedName("quote")
    val quote: Map<String, Data>,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("total_supply")
    val totalSupply: Double
)

internal data class Status(
    @SerializedName("credit_count")
    val creditCount: Int,
    @SerializedName("elapsed")
    val elapsed: Int,
    @SerializedName("error_code")
    val errorCode: Int,
    @SerializedName("timestamp")
    val timestamp: String,
    @SerializedName("total_count")
    val totalCount: Int
)

internal data class Platform(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("token_address")
    val tokenAddress: String
)

internal data class Data(
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("market_cap")
    val marketCap: Double,
    @SerializedName("percent_change_1h")
    val percentChange1h: Double,
    @SerializedName("percent_change_24h")
    val percentChange24h: Double,
    @SerializedName("percent_change_30d")
    val percentChange30d: Double,
    @SerializedName("percent_change_60d")
    val percentChange60d: Double,
    @SerializedName("percent_change_7d")
    val percentChange7d: Double,
    @SerializedName("percent_change_90d")
    val percentChange90d: Double,
    @SerializedName("price")
    val price: Double,
    @SerializedName("volume_24h")
    val volume24h: Double
)
