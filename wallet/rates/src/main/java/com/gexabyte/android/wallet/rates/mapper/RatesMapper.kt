package com.gexabyte.android.wallet.rates.mapper

import com.gexabyte.android.wallet.rates.CoinMarketData
import com.gexabyte.android.wallet.rates.data.response.ListingData
import com.gexabyte.android.wallet.rates.database.entity.CoinMarketEntity

internal fun ListingData.toCoinMarketEntity(convert: String) = CoinMarketEntity(
    coinMarketId = this.id.toLong(),
    symbol = this.symbol,
    name = this.name,
    price = this.quote[convert]!!.price,
    volume24h = this.quote[convert]!!.volume24h,
    percentChange1h = this.quote[convert]!!.percentChange1h,
    percentChange24h = this.quote[convert]!!.percentChange24h,
    percentChange7d = this.quote[convert]!!.percentChange7d,
    percentChange30d = this.quote[convert]!!.percentChange30d,
    percentChange60d = this.quote[convert]!!.percentChange60d,
    percentChange90d = this.quote[convert]!!.percentChange90d,
    marketCap = this.quote[convert]!!.marketCap,
    lastUpdateString = this.quote[convert]!!.lastUpdated
)

internal fun CoinMarketEntity.toCoinMarketDTO() = CoinMarketData(
    id = this.coinMarketId,
    symbol = this.symbol,
    name = this.name,
    price = this.price,
    priceChange1h = this.percentChange1h,
    priceChange30d = this.percentChange30d
)