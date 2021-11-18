package sample.ktutorial.docs.flow.builders.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import sample.ktutorial.helpers.bigdecimal.BigInt

private fun fb() = flow {
    var x = BigInt.ZERO
    var y = BigInt.ONE
    while (true) {
        emit(x)
        delay(500L)
        val z = x + y
        x = y
        y = z
    }
}


fun main() = runBlocking {
    fb().take(100).collect { println(it) }
}