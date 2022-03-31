package com.gexabyte.android.wallet.core.domain

import wallet.core.jni.HDWallet

interface InitWalletRepository {

    suspend fun generateMnemonicPhrase(length: Int = 128): String

    suspend fun getMnemonicFromHDWallet(hdWallet: HDWallet): String

    suspend fun saveWallet(mnemonic: String)

    suspend fun clearStore()

    fun initWalletFromMnemonicPhrase(mnemonic: String): HDWallet

    fun validateMnemonic(mnemonic: String): Boolean

    suspend fun loadWalletMnemonic(): String

    suspend fun isWalletSaved(): Boolean
}
