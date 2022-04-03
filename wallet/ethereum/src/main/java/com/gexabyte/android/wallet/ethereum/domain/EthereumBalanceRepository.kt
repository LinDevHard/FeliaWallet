package com.gexabyte.android.wallet.ethereum.domain

import kotlinx.coroutines.flow.Flow
import java.math.BigInteger

interface EthereumBalanceRepository {

    fun getEthBalance(address: String): Flow<BigInteger>

    fun getTokenBalance(symbol: String, tokenAddress: String, address: String): Flow<BigInteger>
}