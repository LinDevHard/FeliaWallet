package com.gexabyte.android.wallet.rates.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gexabyte.android.wallet.rates.database.entity.CoinMarketEntity
import kotlinx.coroutines.flow.Flow


@Dao
internal interface CmcDao {

    @Query("SELECT * FROM coin_market_data")
    fun observeCmc(): Flow<List<CoinMarketEntity>>

    @Query("SELECT * FROM coin_market_data WHERE symbol IN (:symbols)")
    fun observeCmcBySymbols(vararg symbols: String): Flow<List<CoinMarketEntity>>

    @Query("SELECT * FROM coin_market_data WHERE symbol = :symbol")
    fun observeCmcBySymbol(symbol: String): Flow<CoinMarketEntity>

    @Query("SELECT * FROM coin_market_data WHERE symbol = :symbol LIMIT 1")
    suspend fun getCmcBySymbol(symbol: String): CoinMarketEntity?

    @Query("SELECT * FROM coin_market_data WHERE coinMarketId = :id LIMIT 1")
    suspend fun getCmcById(id: Long): CoinMarketEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wallet: CoinMarketEntity)

    @Update
    suspend fun update(wallet: CoinMarketEntity)

    @Query("DELETE FROM coin_market_data")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<CoinMarketEntity>)
}
