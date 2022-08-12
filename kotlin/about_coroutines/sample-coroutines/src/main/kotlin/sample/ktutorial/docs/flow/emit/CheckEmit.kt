package sample.ktutorial.docs.flow.emit

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import java.math.BigInteger

/*
    https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/flow.html
 */


fun fibonacci() = flow<BigInteger> {
    var x = BigInteger.ZERO
    var y = BigInteger.ONE
    while (true) {
        emit(x)
        x = y - x
        y += x
    }
}


suspend fun main() {
    fibonacci().take(100).collect(::println)
}