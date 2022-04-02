package com.lindevhard.felia.wallet.main.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.lindevhard.felia.wallet.main.database.entity.WalletEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallets(wallets: List<WalletEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateWallet(wallet: WalletEntity)

    @Query("SELECT * FROM wallets WHERE id=:id LIMIT 1")
    suspend fun findWalletById(id: Long): WalletEntity?

    @Query("SELECT * FROM wallets WHERE id=:id LIMIT 1")
    fun flowOnWalletById(id: Long): Flow<WalletEntity>

    @Query("SELECT * FROM wallets")
    fun flowOnWallets(): Flow<List<WalletEntity>>

    @Query("DELETE FROM wallets")
    suspend fun deleteAll()
}