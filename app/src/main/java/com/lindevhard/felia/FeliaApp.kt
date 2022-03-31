package com.lindevhard.felia

import android.app.Application
import com.gexabyte.android.wallet.core.di.walletCoreModule
import com.lindevhard.felia.component.root.di.featureModules
import com.lindevhard.felia.component.root.di.rootModule
import com.lindevhard.felia.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class FeliaApp : Application() {

    init {
        System.loadLibrary("TrustWalletCore")
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@FeliaApp)

            modules(appModule)
            modules(walletCoreModule)
            modules(rootModule + featureModules)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}