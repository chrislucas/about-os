package sample.ktutorial.docs.composing.asyncstyle

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/*
    https://kotlinlang.org/docs/composing-suspending-functions.html#async-style-functions
 */


private suspend fun a(): Int {
    delay(1000L)
    return 15
}

private suspend fun b(): Int {
    delay(1000L)
    return 32
}

@OptIn(DelicateCoroutinesApi::class)
private fun aAsync() = GlobalScope.async {
    a()
}

@OptIn(DelicateCoroutinesApi::class)
private fun bAsync() = GlobalScope.async {
    b()
}

private fun check() {
    val timer = measureTimeMillis {
        // aAsync e bAsync nao sao funcoes suspensas
        val p = aAsync()
        val q = bAsync()

        runBlocking {
            println("${p.await() + q.await()}")
        }
    }
    println("Time: $timer")
}

fun main() {
    check()
}