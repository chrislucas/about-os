package sample.ktutorial.docs.composing.structured

import kotlinx.coroutines.*
import sample.ktutorial.docs.basics.scope.concurrency.coroutineScopeIO
import sample.ktutorial.docs.basics.scope.concurrency.localDateTimeNow

private suspend fun check(exec: suspend () -> Unit) =
    coroutineScope {
        async { exec() }
    }.await()


private fun checkTestDelay() {

    coroutineScopeIO.launch {
        launch {
            check {
                delay(1000L)
                println("Iniciado async 1 $localDateTimeNow")
            }
            println("terminado async 1 $localDateTimeNow")
        }


        launch {
            check {
                println("Iniciado async 3 $localDateTimeNow")
                delay(500L)
            }
            println("terminado async 3 $localDateTimeNow")
        }
    }

    runBlocking {
        // aqui dentro a execucao e sequencial
        // fora desse bloco as duas coroutines rodam em paralelo
        launch {
            check {
                delay(500L)
                println("Iniciando async 2 $localDateTimeNow")
            }
            println("terminado async 2 $localDateTimeNow")
        }

        delay(2500L)
        println("Iniciando RunBlocking $localDateTimeNow")
    }
}


fun main() {
    checkTestDelay()
}