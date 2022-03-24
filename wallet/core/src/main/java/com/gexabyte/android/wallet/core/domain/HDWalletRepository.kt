package com.gexabyte.android.wallet.core.domain

import wallet.core.jni.CoinType

interface HDWalletRepository {

    fun getPrivateKey(coinType: CoinType): String

    fun getAddress(coinType: CoinType): String
}
