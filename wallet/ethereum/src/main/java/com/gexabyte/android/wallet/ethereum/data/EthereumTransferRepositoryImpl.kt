package com.gexabyte.android.wallet.ethereum.data

import com.gexabyte.android.wallet.core.domain.HDWalletRepository
import com.gexabyte.android.wallet.ethereum.contract.ERC20Contract
import com.gexabyte.android.wallet.ethereum.domain.EthereumTransferRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.reactive.asFlow
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.tx.ChainIdLong
import org.web3j.tx.RawTransactionManager
import org.web3j.tx.Transfer
import org.web3j.tx.gas.StaticGasProvider
import org.web3j.utils.Convert
import wallet.core.jni.CoinType
import java.math.BigDecimal
import java.math.BigInteger

class EthereumTransferRepositoryImpl(
    private val web3: Web3j,
    private val hdWalletRepository: HDWalletRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : EthereumTransferRepository {

    private val staticGasProvider by lazy {
        StaticGasProvider(BigInteger.valueOf(10000000000), BigInteger.valueOf(900_000))
    }

    override fun transferEth(toAddress: String, amount: BigDecimal): Flow<TransactionReceipt> {
        val privateKey = hdWalletRepository.getPrivateKey(CoinType.ETHEREUM)

        return Transfer.sendFunds(
            web3,
            Credentials.create(privateKey),
            toAddress,
            amount,
            Convert.Unit.WEI,
        ).flowable()
            .asFlow()
            .flowOn(ioDispatcher)
    }

    override fun transferTokens(
        toAddress: String,
        tokenAddress: String,
        amount: BigDecimal,
        symbol: String
    ): Flow<TransactionReceipt> {
        val privateKey = hdWalletRepository.getPrivateKey(CoinType.ETHEREUM)

        val contract = ERC20Contract.load(
            tokenAddress,
            web3,
            createRawTransactionManager(privateKey),
            staticGasProvider
        )

        return contract.transfer(toAddress, amount.toBigInteger())
            .flowable()
            .asFlow()
            .flowOn(ioDispatcher)
    }

    override fun validateAddress(address: String): Boolean {
        return """^0x[a-fA-F0-9]{40}""".toRegex().containsMatchIn(address)
    }

    private fun createRawTransactionManager(privateKey: String): RawTransactionManager {
        return RawTransactionManager(
            web3,
            Credentials.create(privateKey),
            ChainIdLong.KOVAN,
        )
    }
}