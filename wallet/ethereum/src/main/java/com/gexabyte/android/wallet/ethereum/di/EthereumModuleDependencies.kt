package com.gexabyte.android.wallet.ethereum.di

import org.web3j.protocol.Web3j

interface EthereumModuleDependencies  {

    fun provideWeb3j(): Web3j
}