package com.gexabyte.android.wallet.binance.domain

import kotlinx.coroutines.flow.Flow
import java.math.BigInteger

interface BinanceBalanceRepository {

    fun getTokenBalance(symbol: String, address: String): Flow<String>
}