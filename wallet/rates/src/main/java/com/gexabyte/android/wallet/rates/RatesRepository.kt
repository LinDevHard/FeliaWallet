package com.gexabyte.android.wallet.rates

import kotlinx.coroutines.flow.Flow

interface RatesRepository {

    fun flowOnAllCmcPrice(): Flow<List<CoinMarketDataDTO>>

    fun flowOnCmcPrice(symbol: String): Flow<CoinMarketDataDTO>

    suspend fun getCmcPrice(symbol: String): CoinMarketDataDTO?

    suspend fun updateAllCmcData(convertCurrency: String = "USD"): Result<Unit>
}