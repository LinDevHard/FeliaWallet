package com.lindevhard.felia.wallet.main.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "wallets")
data class WalletEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val address: String,
    val coinType: Int,
    val symbol: String,
    val name: String,
    val balance: BigDecimal,
)