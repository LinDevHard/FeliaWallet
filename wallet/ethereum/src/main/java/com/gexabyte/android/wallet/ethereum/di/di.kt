package com.gexabyte.android.wallet.ethereum.di

import com.gexabyte.android.wallet.ethereum.data.EthereumBalanceRepositoryImpl
import com.gexabyte.android.wallet.ethereum.data.EthereumTransferRepositoryImpl
import com.gexabyte.android.wallet.ethereum.domain.EthereumBalanceRepository
import com.gexabyte.android.wallet.ethereum.domain.EthereumTransferRepository
import org.koin.dsl.module

val ethereumModule = module {
    single<EthereumBalanceRepository> {
        EthereumBalanceRepositoryImpl(
            get<EthereumModuleDependencies>().provideWeb3j(),
        )
    }
    single<EthereumTransferRepository> {
        EthereumTransferRepositoryImpl(
            get<EthereumModuleDependencies>().provideWeb3j(),
            get(),
        )
    }
}