package com.gexabyte.android.wallet.ethereum.domain

import kotlinx.coroutines.flow.Flow
import org.web3j.protocol.core.methods.response.TransactionReceipt
import java.math.BigDecimal

interface EthereumTransferRepository {

    fun transferEth(toAddress: String, amount: BigDecimal): Flow<TransactionReceipt>

    fun transferTokens(
        toAddress: String,
        tokenAddress: String,
        amount: BigDecimal,
        symbol: String
    ): Flow<TransactionReceipt>

    fun validateAddress(address: String): Boolean
}
