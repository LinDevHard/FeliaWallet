package com.lindevhard.felia.wallet.main.domain.model

import java.math.BigDecimal

data class Wallet(
    val id: Long,
    val address: String,
    val name: String,
    val symbol: String,
    val logo: String,
    val balance: BigDecimal,
    val fiatBalance: BigDecimal,
    val fiatRate: BigDecimal,
    val coinType: Int,
    val contractAddress: String? = null,
) {
    fun sameAddress(address: String?): Boolean {
        return this.address.equals(address, ignoreCase = true)
    }
}