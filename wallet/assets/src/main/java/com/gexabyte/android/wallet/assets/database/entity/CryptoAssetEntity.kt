package com.gexabyte.android.wallet.assets.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assets")
internal data class CryptoAssetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val symbol: String,
    val description: String,
    val decimals: Int,
    val website: String,
    val type: String,
    val explorer: String,
    val network: Int,
    val logo: String,
    val contractAddress: String? = null,
)