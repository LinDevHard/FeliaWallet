package com.gexabyte.android.wallet.assets.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assets_version")
internal data class AssetsVersionEntity(
    @PrimaryKey val id: Long = 0L,
    @ColumnInfo(name = "version") val version: Long
)