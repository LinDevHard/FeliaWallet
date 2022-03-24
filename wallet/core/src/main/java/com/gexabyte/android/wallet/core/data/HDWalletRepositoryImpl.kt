package com.gexabyte.android.wallet.core.data

import com.gexabyte.android.wallet.core.domain.HDWalletRepository
import com.gexabyte.android.wallet.core.utils.toHex
import wallet.core.jni.CoinType
import wallet.core.jni.HDWallet

internal class HDWalletRepositoryImpl(
    private val wallet: HDWallet
) : HDWalletRepository {
    override fun getPrivateKey(coinType: CoinType): String {
        return wallet.getKeyForCoin(coinType).data().toHex()
    }

    override fun getAddress(coinType: CoinType): String {
        return wallet.getAddressForCoin(coinType)
    }
}