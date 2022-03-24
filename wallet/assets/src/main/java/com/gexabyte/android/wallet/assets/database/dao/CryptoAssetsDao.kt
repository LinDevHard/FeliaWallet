package com.gexabyte.android.wallet.assets.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gexabyte.android.wallet.assets.database.entity.CryptoAssetEntity

@Dao
internal interface CryptoAssetsDao {

    @Query("SELECT * FROM assets WHERE symbol= :symbol AND network = :coinType")
    suspend fun getAssetBySymbolAndCoinType(symbol: String, coinType: Int): CryptoAssetEntity?

    @Query("SELECT * FROM assets WHERE symbol= :symbol LIMIT 1")
    suspend fun getAssetBySymbol(symbol: String): CryptoAssetEntity?

    @Query("DELETE FROM assets")
    suspend fun dropAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewAsset(asset: CryptoAssetEntity)
}