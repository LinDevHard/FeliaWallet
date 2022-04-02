package com.lindevhard.felia.wallet.main.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lindevhard.felia.wallet.main.database.convertors.BigDecimalDoubleTypeConverter
import com.lindevhard.felia.wallet.main.database.dao.WalletDao
import com.lindevhard.felia.wallet.main.database.entity.WalletEntity

@Database(
    entities = [
        WalletEntity::class
    ],
    version = 1,
    exportSchema = true,
)

@TypeConverters(BigDecimalDoubleTypeConverter::class)
internal abstract class FeliaDb: RoomDatabase() {
    abstract fun walletDao(): WalletDao
}