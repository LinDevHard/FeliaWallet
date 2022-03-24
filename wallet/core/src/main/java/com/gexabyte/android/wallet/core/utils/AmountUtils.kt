package com.gexabyte.android.wallet.core.utils

import kotlin.math.pow

fun Double.toBigInteger(pow: Int = 1): Double {
    return this * 10.0.pow(pow)
}
