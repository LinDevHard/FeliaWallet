package com.gexabyte.android.wallet.binance.domain

import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface BinanceTransferRepository {

    fun transfer(toAddress: String, symbol: String, amount: BigDecimal): Flow<Unit>

    fun validateAddress(address: String): Boolean
}