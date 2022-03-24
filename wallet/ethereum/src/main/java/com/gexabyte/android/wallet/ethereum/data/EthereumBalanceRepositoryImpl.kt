package com.gexabyte.android.wallet.ethereum.data

import com.gexabyte.android.wallet.ethereum.domain.EthereumBalanceRepository
import com.gexabyte.android.wallet.ethereum.address.ERC20AddressProvider
import com.gexabyte.android.wallet.ethereum.contract.ERC20Contract
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.tx.ClientTransactionManager
import org.web3j.tx.gas.StaticGasProvider
import java.math.BigInteger

class EthereumBalanceRepositoryImpl(
    private val web3: Web3j,
    private val erc20AddressProvider: ERC20AddressProvider,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : EthereumBalanceRepository {

    private val staticGasProvider by lazy {
        StaticGasProvider(BigInteger.valueOf(50000000000), BigInteger.valueOf(300_000))
    }

    override fun getEthBalance(address: String): Flow<BigInteger> {
        return web3.ethGetBalance(address, DefaultBlockParameterName.LATEST)
                .flowable()
                .asFlow()
                .map { it.balance }
                .flowOn(ioDispatcher)

    }

    override fun getTokenBalance(symbol: String, address: String): Flow<BigInteger> {
        val tokenAddress = erc20AddressProvider.getAddressBySymbol(symbol)

        val contract = ERC20Contract.load(
            tokenAddress,
            web3,
            ClientTransactionManager(web3, address),
            staticGasProvider
        )

        return contract.balanceOf(address)
            .flowable()
            .asFlow()
            .flowOn(ioDispatcher)
    }
}