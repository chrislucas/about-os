package sample.ktutorial.helpers.integer

/**
 * https://www.mathscareers.org.uk/calculating-eulers-constant-e/
 * https://www.mathsisfun.com/numbers/e-eulers-number.html
 * */


fun exp(b: Double, e: Long): Double {
    return if (e == 0L) {
        1.0
    }
    else if (e == 1L) {
        b
    } else {
        var acc = 1.0

        var ce = e
        var cb = b
        while (ce > 0) {
            if (ce and 1L == 1L) {
                acc *= cb
            }
            cb *= cb
            ce = ce shr 1
        }
        acc
    }
}

private fun checkExp() {
    println(exp(10.0, 3))
    println(exp(5.0, 3))
    println(exp(2.0, 15))
    println(exp(12.0, 15))
    println(exp(8.0, 15))
}

private fun calculateEulerNumber(e: Long) = exp(1.0 + (1.0 / e), e)

private fun checkCalculateEulerNumber() {
    var i = 1L
    val limit = 1000L//(1 shl 31) - 1
    while (i <= limit) {
        println("$i, ${calculateEulerNumber(i)}")
        i *= 5L
    }
}

fun main() {
    //checkExp()
    checkCalculateEulerNumber()
}