package com.gexabyte.android.wallet.assets.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gexabyte.android.wallet.assets.database.dao.AssetsVersionDao
import com.gexabyte.android.wallet.assets.database.dao.CryptoAssetsDao
import com.gexabyte.android.wallet.assets.database.entity.AssetsVersionEntity
import com.gexabyte.android.wallet.assets.database.entity.CryptoAssetEntity

@Database(
    entities = [
        AssetsVersionEntity::class,
        CryptoAssetEntity::class
    ], version = 1
)
internal abstract class AssetsDatabase: RoomDatabase() {
    abstract fun versionDao(): AssetsVersionDao
    abstract fun assetsDao(): CryptoAssetsDao
}