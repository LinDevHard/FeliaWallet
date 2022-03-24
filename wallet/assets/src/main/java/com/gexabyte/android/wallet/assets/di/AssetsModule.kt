package com.gexabyte.android.wallet.assets.di

import android.content.Context
import androidx.room.Room
import com.gexabyte.android.wallet.assets.AssetsRepository
import com.gexabyte.android.wallet.assets.database.AssetsDatabase
import com.gexabyte.android.wallet.assets.impl.AssetsRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val assetsModule = module {
    single { provideAssetsDatabase(get()) }
    single { get<AssetsDatabase>().assetsDao() }
    single { get<AssetsDatabase>().versionDao() }

    single<AssetsRepository> {
        AssetsRepositoryImpl(
            get<Context>().assets,
            get(),
            get(),
            CoroutineScope(SupervisorJob() + Dispatchers.IO
            )
        )
    }
}

private fun provideAssetsDatabase(context: Context): AssetsDatabase {
    return Room.databaseBuilder(context, AssetsDatabase::class.java, "assets.db")
        .fallbackToDestructiveMigration()
        .build()
}