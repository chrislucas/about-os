package sample.ktutorial.docs.cancelations.withsuspenfun

import kotlinx.coroutines.*
import sample.ktutorial.docs.basics.scope.concurrency.dtf
import java.time.LocalDateTime

/*
 quando o job vem da funcao coroutineScope o comportamento de cancelar o job eh diferente. Ate o momento
 estou estudando porque o Job nao esta sendo cancelado na funcao checkCancelAndJoinCallSeparately
* */

private suspend fun getJob() =
    coroutineScope {
        launch {
            repeat(100_000_000) {
                print("Coroutine: $it -> ${dtf.format(LocalDateTime.now())}")
                delay(500L)
                print(" After Job $it\n")
            }
        }
    }


private fun execute(fn: suspend CoroutineScope.() -> Unit) =
    runBlocking {
        fn()
    }


private fun checkCancelAndJoinCallSeparately() {
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

fun main() {
    checkCancelAndJoinCallSeparately()
}
