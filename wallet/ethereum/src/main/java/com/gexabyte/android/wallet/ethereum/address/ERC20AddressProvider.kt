package com.gexabyte.android.wallet.ethereum.address

interface ERC20AddressProvider {
    val usdt: String

    fun getAddressBySymbol(symbol: String): String
}