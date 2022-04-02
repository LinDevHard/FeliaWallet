package com.lindevhard.felia.wallet.main.database.convertors

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.math.BigDecimal

class BigDecimalDoubleTypeConverter {

    @TypeConverter
    fun bigDecimalToDouble(input: BigDecimal?): Double {
        return input?.toDouble() ?: 0.0
    }

    @TypeConverter
    fun stringToBigDecimal(input: Double?): BigDecimal {
        if (input == null) return BigDecimal.ZERO
        return BigDecimal.valueOf(input) ?: BigDecimal.ZERO
    }

}