package com.gexabyte.android.wallet.rates

import kotlinx.coroutines.flow.Flow

interface RatesRepository {

    fun flowOnAllCmcPrice(): Flow<List<CoinMarketData>>

    fun flowOnCmcPrice(symbol: String): Flow<CoinMarketData>

    suspend fun getCmcPrice(symbol: String): CoinMarketData?

    suspend fun updateAllCmcData(convertCurrency: String = "USD"): Result<Unit>
}