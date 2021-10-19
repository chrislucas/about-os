package sample.ktutorial.docs.cancelations

import kotlinx.coroutines.*
import sample.ktutorial.docs.basics.scope.concurrency.dtf

/*
    https://kotlinlang.org/docs/cancellation-and-timeouts.html#cancelling-coroutine-execution
 */


fun execute(fn: suspend CoroutineScope.() -> Unit) = runBlocking {
    fn()
}


private fun getJob() =
    CoroutineScope(Dispatchers.Default).launch {
        launch {
            repeat(100_000_000) {
                print("Coroutine: $it -> ${dtf.format(java.time.LocalDateTime.now())}")
                delay(500L)
                print(" After Job $it\n")
            }
        }
    }


fun cancelAndJoinCallsSeparately() {
    execute {

        val job: Job = getJob()

        delay(3000L)
        println("Em execucao")
        job.cancel()
        println("Job cancelado")
        job.join() // aguarda para que o Job seja completado
        println("Aguardando a completudo do Job")
    }
}

private fun cancelAndJoinCallsJoint() {
    execute {
        val job = getJob()
        delay(3000L)
        println("\nEm execucao")
        job.cancelAndJoin()
        println("Job cancelado")
    }
}

private fun justCancelJob() {
    execute {
        val job = getJob()
        delay(3000L)
        println("\nEm execucao")
        job.cancel()
        println("Job cancelado")
    }
}


fun main() {
    cancelAndJoinCallsJoint()
}