package sample.ktutorial.basics.scope.concurrency

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
    https://kotlinlang.org/docs/coroutines-basics.html#scope-builder-and-concurrency

    CoroutineScope builder pode ser usado em qualquer suspend function para executar multiplas
    operacoes

 */


private suspend fun sample() = coroutineScope {
    launch {
        delay(2000L)
        println("2")
    }

    launch {
        delay(1000L)
        println("1")
    }

    println("0")
}

fun checkSample() = runBlocking {
    sample()
}

fun main() {
    checkSample()
}