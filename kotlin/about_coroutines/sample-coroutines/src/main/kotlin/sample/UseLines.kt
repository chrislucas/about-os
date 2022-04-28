package sample

import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * https://kotlin-quick-reference.com/050-R-input-output-io.html
 */
fun checkFunUseLines() {
    val s = BufferedReader(InputStreamReader(System.`in`))
        .useLines {
            it
        }
    //println(s.joinToString(", "))
}

fun main() {
    checkFunUseLines()
}