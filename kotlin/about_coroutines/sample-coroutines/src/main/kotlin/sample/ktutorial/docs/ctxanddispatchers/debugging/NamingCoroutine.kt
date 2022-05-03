package sample.ktutorial.docs.ctxanddispatchers.debugging

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import sample.ktutorial.docs.logCoroutineScope
import kotlin.math.log

/**
 * https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html#naming-coroutines-for-debugging
 */


fun main() =
    runBlocking (CoroutineName("MAIN_DEBUG")) {
        logCoroutineScope("Start Main coroutine")

        val p = async (CoroutineName("1st_async_fun")) {
            delay(500)
            logCoroutineScope("Primeira async fun")
            12
        }

        val q = async (CoroutineName("2nd_async_fun")) {
            delay(1500)
            logCoroutineScope("Segunda async fun")
            24
        }

        logCoroutineScope("Resultado das funcoes assincronas: ${p.await() + q.await()}")
    }
