package com.gexabyte.android.wallet.binance.data

import com.binance.dex.api.client.BinanceDexApi
import com.binance.dex.api.client.BinanceDexApiRestClient
import com.gexabyte.android.wallet.binance.domain.BinanceBalanceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BinanceBalanceRepositoryImpl(
    private val client: BinanceDexApiRestClient,
): BinanceBalanceRepository {

    override fun getTokenBalance(symbol: String, address: String): Flow<String> = flow {
        val balanceAccount = client.getAccount(address).balances.find { it.symbol == symbol }
        if(balanceAccount != null) {
            emit(balanceAccount.free)
        } else {
            emit("0.0")
        }
    }
}