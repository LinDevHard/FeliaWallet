package com.gexabyte.android.wallet.core.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import com.gexabyte.android.wallet.core.Wallet
import com.gexabyte.android.wallet.core.data.CreateWalletRepositoryImpl
import com.gexabyte.android.wallet.core.data.HDWalletRepositoryImpl
import com.gexabyte.android.wallet.core.data.datastore.WalletSerializer
import com.gexabyte.android.wallet.core.domain.HDWalletRepository
import com.gexabyte.android.wallet.core.domain.InitWalletRepository
import com.gexabyte.android.wallet.core.domain.MemoryStorage
import com.gexabyte.android.wallet.core.domain.WalletStorage
import com.google.crypto.tink.Aead
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.aead.AesGcmKeyManager
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import org.koin.core.qualifier.named
import org.koin.dsl.module
import wallet.core.jni.HDWallet
import java.io.File

private const val KEYSET_NAME = "master_keyset"
private const val PREFERENCE_FILE = "master_key_preference"
private const val MASTER_KEY_URI = "android-keystore://master_key"

val walletCoreModule = module {
    single(createdAtStart = true) { provideAead(get()) }
    single(named("wallet_store")) { provideWalletDataStore(get(), get()) }
    single { provideHDWallet(get(), get()) }
    single<InitWalletRepository> { CreateWalletRepositoryImpl(get(named("wallet_store")), get(), get()) }
    factory <HDWalletRepository> { HDWalletRepositoryImpl(get()) }
    single<MemoryStorage> { WalletStorage }
}

fun provideHDWallet(repo: InitWalletRepository, storage: MemoryStorage): HDWallet {
    return repo.initWalletFromMnemonicPhrase(storage.mnemonic)
}

fun provideWalletDataStore(context: Context, aead: Aead): DataStore<Wallet> {

    return DataStoreFactory.create(
        produceFile = { File(context.applicationContext.filesDir, "datastore/wallet.proto") },
        serializer = WalletSerializer(aead),
    )
}

fun provideAead(application: Application): Aead {
    AeadConfig.register()

    return AndroidKeysetManager.Builder()
        .withSharedPref(application, KEYSET_NAME, PREFERENCE_FILE)
        .withKeyTemplate(AesGcmKeyManager.aes256GcmTemplate())
        .withMasterKeyUri(MASTER_KEY_URI)
        .build()
        .keysetHandle
        .getPrimitive(Aead::class.java)
}
