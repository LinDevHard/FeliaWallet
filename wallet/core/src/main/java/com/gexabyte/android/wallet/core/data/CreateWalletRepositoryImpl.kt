package com.gexabyte.android.wallet.core.data

import android.util.Log
import androidx.datastore.core.DataStore
import com.gexabyte.android.wallet.core.Wallet
import com.gexabyte.android.wallet.core.domain.InitWalletRepository
import com.gexabyte.android.wallet.core.domain.MemoryStorage
import com.gexabyte.android.wallet.core.domain.WalletStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import wallet.core.jni.HDWallet
import wallet.core.jni.Mnemonic
import java.security.InvalidParameterException

internal class CreateWalletRepositoryImpl(
    private val walletDataStore: DataStore<Wallet>,
    private val externalScope: CoroutineScope,
    private val mnemonicStorage: MemoryStorage,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : InitWalletRepository {

    override suspend fun generateMnemonicPhrase(length: Int): String =
        withContext(Dispatchers.Default) {
            HDWallet(length, "").mnemonic()
        }

    override fun initWalletFromMnemonicPhrase(mnemonic: String): HDWallet {
        return if (HDWallet.isValid(mnemonic)) {
            HDWallet(mnemonic, "")
        } else {
            throw InvalidParameterException()
        }
    }

    override fun validateMnemonic(mnemonic: String): Boolean {
        return HDWallet.isValid(mnemonic)
    }

    override suspend fun getMnemonicFromHDWallet(hdWallet: HDWallet): String =
        hdWallet.mnemonic()

    override suspend fun saveWallet(mnemonic: String) {
        if (HDWallet.isValid(mnemonic)) {
            externalScope.launch {
                walletDataStore.updateData { preferences ->
                    preferences.toBuilder()
                        .setSeedPhrase(mnemonic)
                        .setPassPhrase("")
                        .build()
                }
            }
        } else {
            throw InvalidParameterException()
        }
    }

    override suspend fun clearStore() {
        externalScope.launch {
            withContext(ioDispatcher) {
                walletDataStore.updateData { preferences ->
                    preferences.toBuilder()
                        .setPassPhrase("")
                        .setSeedPhrase("")
                        .build()
                }
            }
        }
    }

    override suspend fun loadWalletMnemonic(): String {
        return walletDataStore.data.first().seedPhrase
    }

    override suspend fun isWalletSaved(): Boolean {
        return withContext(ioDispatcher) {
            val isSaved = walletDataStore.data.map { wallet ->
                !wallet.seedPhrase.isNullOrEmpty()
            }.first()

            if(isSaved) {
                mnemonicStorage.mnemonic = walletDataStore.data.map { wallet ->
                    wallet.seedPhrase ?: ""
                }.first()
            }

            isSaved
        }
    }
}
