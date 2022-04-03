package com.lindevhard.felia.utils

import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.math.abs
import kotlin.math.pow

object BalanceUtils {
    private const val weiInEth = "1000000000000000000"
    private const val showDecimalPlaces = 5
    private const val MACRO_PATTERN = "###,###,###,###,##0"
    private const val CURRENCY_PATTERN = "$MACRO_PATTERN.00"

    private const val TOKEN_BALANCE_PRECISION = 4
    private const val TOKEN_BALANCE_FOCUS_PRECISION = 5
    private const val ONE_BILLION = 1000000000.0
    private fun getDigitalPattern(precision: Int): String {
        return getDigitalPattern(precision, 0)
    }

    /*
    Use this to format display strings however you like, eg for French, Spanish style
    If you change this, it would also be advisable to change NumericInput so user has expected input format
    */
    private fun getFormat(pattern: String): DecimalFormat {
        val standardisedNumericFormat = DecimalFormatSymbols(Locale.ENGLISH)
        standardisedNumericFormat.decimalSeparator = '.'
        standardisedNumericFormat.groupingSeparator = ','
        return DecimalFormat(pattern, standardisedNumericFormat)
    }

    private fun getDigitalPattern(precision: Int, fixed: Int): String {
        val sb = StringBuilder()
        sb.append(MACRO_PATTERN)
        if (precision > 0) {
            sb.append(".")
            for (i in 0 until fixed) sb.append("0")
            for (i in 0 until precision - fixed) sb.append("#")
        }
        return sb.toString()
    }

    private fun convertToLocale(value: String): String {
        return value
    }

    fun weiToEth(wei: BigDecimal?): BigDecimal {
        return Convert.fromWei(wei, Convert.Unit.ETHER)
    }

    fun ethToUsd(priceUsd: String?, ethBalance: String?): String {
        var usd: BigDecimal = BigDecimal(ethBalance).multiply(BigDecimal(priceUsd))
        usd = usd.setScale(2, RoundingMode.DOWN)
        return usd.toString()
    }

    fun EthToWei(eth: String?): String {
        val wei: BigDecimal = BigDecimal(eth).multiply(BigDecimal(weiInEth))
        return wei.toBigInteger().toString()
    }

    fun UnitToEMultiplier(value: String?, decimalPlaces: BigDecimal?): String {
        val `val`: BigDecimal = BigDecimal(value).multiply(decimalPlaces)
        return `val`.toBigInteger().toString()
    }

    fun weiToGweiBI(wei: BigInteger?): BigDecimal {
        return Convert.fromWei(BigDecimal(wei), Convert.Unit.GWEI)
    }

    fun weiToGwei(wei: BigInteger?): String {
        return Convert.fromWei(BigDecimal(wei), Convert.Unit.GWEI).toPlainString()
    }

    fun weiToGweiInt(wei: BigDecimal?): String {
        return getScaledValue(Convert.fromWei(wei, Convert.Unit.GWEI), 0, 0)
    }

    fun weiToGwei(wei: BigDecimal?, precision: Int): String {
        val value: BigDecimal = Convert.fromWei(wei, Convert.Unit.GWEI)
        return scaledValue(value, getDigitalPattern(precision), 0, 0)
        //return getScaledValue(wei, Convert.Unit.GWEI.getWeiFactor().intValue(), precision);
        //return Convert.fromWei(new BigDecimal(wei), Convert.Unit.GWEI).setScale(decimals, RoundingMode.HALF_DOWN).toString(); //to 2 dp
    }

    fun gweiToWei(gwei: BigDecimal?): BigInteger {
        return Convert.toWei(gwei, Convert.Unit.GWEI).toBigInteger()
    }

    /**
     * Base - taken to mean default unit for a currency e.g. ETH, DOLLARS
     * Subunit - taken to mean subdivision of base e.g. WEI, CENTS
     *
     * @param baseAmountStr - decimal amount in base unit of a given currency
     * @param decimals - decimal places used to convert to subunits
     * @return amount in subunits
     */
    fun baseToSubunit(baseAmountStr: String?, decimals: Int): BigInteger {
        assert(decimals >= 0)
        val baseAmount = BigDecimal(baseAmountStr)
        val subunitAmount: BigDecimal = baseAmount.multiply(BigDecimal.valueOf(10).pow(decimals))
        return try {
            subunitAmount.toBigIntegerExact()
        } catch (ex: ArithmeticException) {
            assert(false)
            subunitAmount.toBigInteger()
        }
    }

    /**
     * @param subunitAmount - amount in subunits
     * @param decimals - decimal places used to convert subunits to base
     * @return amount in base units
     */
    fun subunitToBase(subunitAmount: BigInteger?, decimals: Int): BigDecimal {
        assert(decimals >= 0)
        return BigDecimal(subunitAmount).divide(BigDecimal.valueOf(10).pow(decimals))
    }

    fun isDecimalValue(value: String): Boolean {
        for (ch in value.toCharArray()) if (!(Character.isDigit(ch) || ch == '.')) return false
        return true
    }

    fun getScaledValueWithLimit(value: BigDecimal, decimals: Long): String {
        val pattern = getDigitalPattern(9, 2)
        return scaledValue(value, pattern, decimals, 0)
    }

    fun getScaledValueFixed(value: BigDecimal, decimals: Long, precision: Int): String {
        val pattern = getDigitalPattern(precision, precision)
        return scaledValue(value, pattern, decimals, precision)
    }

    fun getScaledValueMinimal(value: BigInteger, decimals: Long): String {
        return getScaledValueMinimal(
            BigDecimal(value),
            decimals,
            TOKEN_BALANCE_FOCUS_PRECISION
        )
    }

    fun getScaledValueMinimal(value: BigDecimal, decimals: Long, max_precision: Int): String {
        return scaledValue(value, getDigitalPattern(max_precision, 0), decimals, 0)
    }

    fun getScaledValueScientific(value: BigDecimal, decimals: Long): String {
        return getScaledValueScientific(value, decimals, showDecimalPlaces)
    }

    //TODO: write 'suffix' generator: https://www.nist.gov/pml/weights-and-measures/metric-si-prefixes
    // Tera to pico (T to p) Anything below p show as 0.000
    fun getScaledValueScientific(value: BigDecimal, decimals: Long, dPlaces: Int): String {
        val returnValue: String
        val correctedValue: BigDecimal = value.divide(
            BigDecimal.valueOf(10.0.pow(decimals.toDouble())),
            18,
            RoundingMode.DOWN
        )
        val displayThreshold: BigDecimal = BigDecimal.ONE.divide(
            BigDecimal.valueOf(10.0.pow(dPlaces.toDouble())),
            18,
            RoundingMode.DOWN
        )
        returnValue = if (value == BigDecimal.ZERO) //zero balance
        {
            "0"
        } else if (correctedValue < displayThreshold) //very low balance //TODO: Fold into getSuffixedValue below
        {
            "0.000~"
        } else if (requiresSuffix(correctedValue, dPlaces)) {
            getSuffixedValue(correctedValue, dPlaces)
        } else
        {
            val df: DecimalFormat = getFormat(getDigitalPattern(dPlaces))
            df.roundingMode = RoundingMode.DOWN
            convertToLocale(df.format(correctedValue))
        }
        return returnValue
    }

    private fun requiresSuffix(correctedValue: BigDecimal, dPlaces: Int): Boolean {
        val displayThreshold: BigDecimal = BigDecimal.ONE.divide(
            BigDecimal.valueOf(Math.pow(10.0, dPlaces.toDouble())),
            18,
            RoundingMode.DOWN
        )
        return (correctedValue < displayThreshold
                || correctedValue > BigDecimal.valueOf(
            10.0.pow((6 + dPlaces).toDouble())
                ))
    }

    private fun getSuffixedValue(correctedValue: BigDecimal, dPlaces: Int): String {
        var correctedValue: BigDecimal = correctedValue
        val df: DecimalFormat = getFormat(getDigitalPattern(0))
        df.roundingMode = RoundingMode.DOWN
        var reductionValue = 0
        var suffix = ""
        when {
            correctedValue > BigDecimal.valueOf(
                10.0.pow((12 + dPlaces).toDouble())
            ) -> {
                reductionValue = 12
                suffix = "T"
            }
            correctedValue > BigDecimal.valueOf(
                10.0.pow((9 + dPlaces).toDouble())
            ) //G
            -> {
                reductionValue = 9
                suffix = "G"
            }
            correctedValue.compareTo(
                BigDecimal.valueOf(
                    Math.pow(
                        10.0,
                        (6 + dPlaces).toDouble()
                    )
                )
            ) > 0 //M
            -> {
                reductionValue = 6
                suffix = "M"
            }
        }
        correctedValue = correctedValue.divideToIntegralValue(
            BigDecimal.valueOf(
                Math.pow(
                    10.0,
                    reductionValue.toDouble()
                )
            )
        )
        return convertToLocale(df.format(correctedValue)) + suffix
    }

    fun getScaledValue(value: BigDecimal, decimals: Long, precision: Int): String {
        return try {
            scaledValue(value, getDigitalPattern(precision), decimals, precision)
        } catch (e: NumberFormatException) {
            "~"
        }
    }

    private fun scaledValue(
        value: BigDecimal,
        pattern: String,
        decimals: Long,
        macroPrecision: Int
    ): String {
        var value: BigDecimal = value
        var df: DecimalFormat = getFormat(pattern)
        value = value.divide(
            BigDecimal.valueOf(Math.pow(10.0, decimals.toDouble())),
            18,
            RoundingMode.DOWN
        )
        if (macroPrecision > 0) {
            val displayThreshold: BigDecimal = BigDecimal.ONE.multiply(
                BigDecimal.valueOf(
                    Math.pow(
                        10.0,
                        macroPrecision.toDouble()
                    )
                )
            )
            if (value.compareTo(displayThreshold) > 0) {
                //strip decimals
                df = getFormat(MACRO_PATTERN)
            }
        }
        df.roundingMode = RoundingMode.DOWN
        return convertToLocale(df.format(value))
    }

    /**
     * Default precision method
     *
     * @param valueStr
     * @param decimals
     * @return
     */
    fun getScaledValue(valueStr: String?, decimals: Long): String {
        return getScaledValue(valueStr, decimals, TOKEN_BALANCE_PRECISION)
    }

    /**
     * Universal scaled value method
     * @param valueStr
     * @param decimals
     * @return
     */
    fun getScaledValue(valueStr: String?, decimals: Long, precision: Int): String {
        // Perform decimal conversion
        return if (decimals > 1 && valueStr != null && valueStr.isNotEmpty() && Character.isDigit(
                valueStr[0]
            )
        ) {
            val value = BigDecimal(valueStr)
            getScaledValue(
                value,
                decimals,
                precision
            ) //represent balance transfers according to 'decimals' contract indicator property
        } else valueStr ?: "0"
    }

    //Currency conversion
    fun genCurrencyString(price: Double, currencySymbol: String): String {
        var price = price
        var suffix = ""
        var format = CURRENCY_PATTERN
        if (price > ONE_BILLION) {
            format += "0"
            price /= ONE_BILLION
            suffix = "B"
        }
        val df: DecimalFormat = getFormat(format)
        df.roundingMode = RoundingMode.CEILING
        return if (price >= 0) {
            currencySymbol + df.format(price).toString() + suffix
        } else {
            "-" + currencySymbol + df.format(abs(price))
        }
    }
}