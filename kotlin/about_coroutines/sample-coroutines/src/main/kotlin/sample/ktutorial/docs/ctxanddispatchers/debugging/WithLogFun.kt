package sample.ktutorial.docs.ctxanddispatchers.debugging

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import sample.ktutorial.docs.logCoroutineScope

/*
    https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html#debugging-using-logging
    https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/jvm/test/guide/example-context-03.kt
 */

private fun check() = runBlocking {
    val a = async {
        logCoroutineScope("First OP")
        6
    }

    val b = async {
        logCoroutineScope("Second OP")
        10
    }
    val r = a.await() + b.await()
    logCoroutineScope("$r")
}


fun main() {
    check()
}