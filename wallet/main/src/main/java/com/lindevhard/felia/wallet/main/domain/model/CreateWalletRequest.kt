package com.lindevhard.felia.wallet.main.domain.model

import com.gexabyte.android.wallet.assets.CryptoAsset
import wallet.core.jni.CoinType

data class CreateWalletRequest(
    val address: String,
    val coinType: CoinType,
    val assets: CryptoAsset,
)