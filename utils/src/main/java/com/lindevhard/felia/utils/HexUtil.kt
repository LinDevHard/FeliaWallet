package com.lindevhard.felia.utils

import android.text.TextUtils
import okhttp3.internal.and
import org.web3j.utils.Numeric
import java.math.BigDecimal
import java.math.BigInteger
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.StandardCharsets

object HexUtil {
    fun hexToInteger(input: String, def: Int): Int {
        val value = hexToInteger(input)
        return value ?: def
    }

    fun hexToInteger(input: String): Int? {
        return try {
            Integer.decode(input)
        } catch (ex: NumberFormatException) {
            null
        }
    }

    fun hexToLong(input: String?, def: Int): Long {
        val value = hexToLong(input)
        return value ?: def.toLong()
    }

    fun hexToLong(input: String?): Long? {
        return try {
            java.lang.Long.decode(input)
        } catch (ex: NumberFormatException) {
            null
        }
    }

    fun hexToBigInteger(input: String?): BigInteger? {
        var input = input
        return if (TextUtils.isEmpty(input)) {
            null
        } else try {
            val isHex = containsHexPrefix(input)
            if (isHex) {
                input = cleanHexPrefix(input)
            }
            BigInteger(input, if (isHex) 16 else 10)
        } catch (ex: NullPointerException) {
            null
        } catch (ex: NumberFormatException) {
            null
        }
    }

    fun hexToBigInteger(input: String?, def: BigInteger): BigInteger {
        return hexToBigInteger(input) ?: def
    }

    fun hexToBigDecimal(input: String?): BigDecimal {
        return BigDecimal(hexToBigInteger(input))
    }

    fun hexToBigDecimal(input: String?, def: BigDecimal): BigDecimal {
        return BigDecimal(hexToBigInteger(input, def.toBigInteger()))
    }

    fun containsHexPrefix(input: String?): Boolean {
        return input!!.length > 1 && input[0] == '0' && input[1] == 'x'
    }

    fun cleanHexPrefix(input: String?): String? {
        var input = input
        if (input != null && containsHexPrefix(input)) {
            input = input.substring(2)
        }
        return input
    }

    fun hexToDecimal(value: String?): String? {
        return hexToBigInteger(value)?.toString(10)
    }

    fun hexStringToByteArray(input: String?): ByteArray {
        val cleanInput = cleanHexPrefix(input)
        if (TextUtils.isEmpty(cleanInput)) {
            return byteArrayOf()
        }
        val len = cleanInput!!.length
        val data: ByteArray
        val startIdx: Int
        if (len % 2 != 0) {
            data = ByteArray(len / 2 + 1)
            data[0] = Character.digit(cleanInput[0], 16).toByte()
            startIdx = 1
        } else {
            data = ByteArray(len / 2)
            startIdx = 0
        }
        var i = startIdx
        while (i < len) {
            data[(i + 1) / 2] = ((Character.digit(cleanInput[i], 16) shl 4)
                    + Character.digit(cleanInput[i + 1], 16)).toByte()
            i += 2
        }
        return data
    }

    fun byteArrayToHexString(
        input: ByteArray,
        offset: Int,
        length: Int,
        withPrefix: Boolean
    ): String {
        val stringBuilder = StringBuilder()
        if (withPrefix) {
            stringBuilder.append("0x")
        }
        for (i in offset until offset + length) {
            stringBuilder.append(String.format("%02x", input[i] and 0xFF))
        }
        return stringBuilder.toString()
    }

    fun byteArrayToHexString(input: ByteArray?): String? {
        return if (input == null || input.isEmpty()) {
            null
        } else byteArrayToHexString(input, 0, input.size, true)
    }

    fun hexToUtf8(hex: String): String {
        var hex = hex
        hex = Numeric.cleanHexPrefix(hex)
        val buff: ByteBuffer = ByteBuffer.allocate(hex.length / 2)
        var i = 0
        while (i < hex.length) {
            buff.put(hex.substring(i, i + 2).toInt(16).toByte())
            i += 2
        }
        buff.rewind()
        val cb: CharBuffer = StandardCharsets.UTF_8.decode(buff)
        return cb.toString()
    }
}