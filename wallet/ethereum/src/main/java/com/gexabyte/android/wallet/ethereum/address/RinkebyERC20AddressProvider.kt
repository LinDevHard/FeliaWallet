package com.gexabyte.android.wallet.ethereum.address

class RinkebyERC20AddressProvider : ERC20AddressProvider {
    override val usdt: String
        get() = "0x09c2fb39c66f213eceab9f75fe6fb3be652ee8eb"

    override fun getAddressBySymbol(symbol: String): String {
        return when(symbol) {
            "USDT" -> usdt
            else -> ""
        }
    }
}