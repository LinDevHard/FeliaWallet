package com.lindevhard.felia.wallet.main.domain.model

import com.gexabyte.android.wallet.assets.CryptoAsset
import com.gexabyte.android.wallet.rates.CoinMarketData
import java.math.BigDecimal

data class WalletDetail(
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
    val asset: CryptoAsset,
    val marketData: CoinMarketData?,
)