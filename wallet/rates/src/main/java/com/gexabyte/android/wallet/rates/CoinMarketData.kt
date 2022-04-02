package com.gexabyte.android.wallet.rates

data class CoinMarketData(
    val id: Long,
    val symbol: String,
    val name: String,
    val price: Double,
    val priceChange1h: Double,
    val priceChange30d: Double,
)