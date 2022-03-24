package com.gexabyte.android.wallet.rates.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_market_data")
internal data class CoinMarketEntity(
    @PrimaryKey val coinMarketId: Long,
    val symbol: String,
    val name: String,
    val price: Double,
    @ColumnInfo(name = "volume_24h") val volume24h: Double,
    @ColumnInfo(name = "percent_change_1h") val percentChange1h: Double,
    @ColumnInfo(name = "percent_change_24h") val percentChange24h: Double,
    @ColumnInfo(name = "percent_change_7d") val percentChange7d: Double,
    @ColumnInfo(name = "percent_change_30d") val percentChange30d: Double,
    @ColumnInfo(name = "percent_change_60d") val percentChange60d: Double,
    @ColumnInfo(name = "percent_change_90d") val percentChange90d: Double,
    @ColumnInfo(name = "market_cap") val marketCap: Double,
    @ColumnInfo(name = "last_updated") val lastUpdateString: String,
)
