package sample.ktutorial.helpers.bigdecimal

import java.math.BigDecimal
import java.math.BigInteger


typealias BigDec = BigDecimal
typealias BigInt = BigInteger


val D_ONE = BigDec.ONE
val I_ONE = BigInt.ONE
val I_ZERO = BigInt.ZERO

val DD_ONE = BigDec("1.0")

fun exp(b: BigDec, e: BigInt): BigDec {

    return if (e == I_ZERO) {
        D_ONE
    } else if (e == I_ONE) {
        b
    } else {
        var cb = b
        var ce = e
        var acc = D_ONE
        while (ce > I_ZERO) {
            if (ce and I_ONE == I_ONE) {
                acc *= cb
            }
            cb *= cb
            ce = ce shr 1
        }
        acc
    }
}

fun checkExp() {
    println(exp(BigDec("3"), BigInt("4")))
    println(exp(BigDec("15"), BigInt("4")))
    println(exp(BigDec("2"), BigInt("14")))
}

private fun calculateEulerNumber(e: BigInt) = exp(BigDec(1.0 + (1.0 / e.toDouble())), e)


private fun checkCalculateEulerNumber() {
    var i = 125
    val limit = 1000
    while (i <= limit) {
        val r = calculateEulerNumber(BigInt("$i"))
        println("$i\n$r\n")
        i *= 5
    }
}

fun main() {
    checkCalculateEulerNumber()
    //checkBigDec()
}