package com.gexabyte.android.wallet.rates.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gexabyte.android.wallet.rates.database.dao.CmcDao
import com.gexabyte.android.wallet.rates.database.entity.CoinMarketEntity

@Database(entities = [CoinMarketEntity::class], version = 1)
internal abstract class RatesDatabase: RoomDatabase() {
    abstract fun cmcDao(): CmcDao
}