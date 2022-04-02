package com.lindevhard.felia.wallet.main.di

import android.content.Context
import androidx.room.Room
import com.lindevhard.felia.wallet.main.data.WalletRepositoryImpl
import com.lindevhard.felia.wallet.main.database.FeliaDb
import com.lindevhard.felia.wallet.main.database.convertors.BigDecimalDoubleTypeConverter
import com.lindevhard.felia.wallet.main.domain.WalletRepository
import com.lindevhard.felia.wallet.main.domain.usecase.CreateWalletUseCase
import org.koin.dsl.module

val walletMainModule = module {
    single { provideFeliaDb(get()) }
    factory { get<FeliaDb>().walletDao() }

    factory<WalletRepository> { WalletRepositoryImpl(get(), get(), get()) }
    factory { CreateWalletUseCase(get(), get()) }
}

private fun provideFeliaDb(context: Context): FeliaDb {
    return Room.databaseBuilder(context, FeliaDb::class.java, "felia.db")
        .fallbackToDestructiveMigration()
        .build()
}