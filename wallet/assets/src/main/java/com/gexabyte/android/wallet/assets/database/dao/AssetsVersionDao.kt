package com.gexabyte.android.wallet.assets.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gexabyte.android.wallet.assets.database.entity.AssetsVersionEntity

@Dao
internal interface AssetsVersionDao {

    @Query("SELECT * FROM assets_version LIMIT 1")
    suspend fun getCurrentVersion(): AssetsVersionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateVersion(version: AssetsVersionEntity)
}