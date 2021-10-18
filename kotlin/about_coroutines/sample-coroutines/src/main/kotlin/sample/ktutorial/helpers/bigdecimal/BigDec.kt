package sample.ktutorial.helpers.bigdecimal

import java.math.RoundingMode

private fun checkBigDec() {

    println(BigDec(1.00).divide(BigDec(10.0), RoundingMode.HALF_EVEN).setScale(3))
    println(BigDec(1.00).divide(BigDec(10.0), RoundingMode.HALF_UP).setScale(3))
    println(BigDec(1.00).divide(BigDec(10.0), RoundingMode.HALF_DOWN).setScale(3))


}