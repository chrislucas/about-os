package sample.ktutorial.docs.cancelations

import kotlinx.coroutines.*
import sample.ktutorial.docs.basics.scope.concurrency.dtf
import java.time.LocalDateTime

/*
    https://kotlinlang.org/docs/cancellation-and-timeouts.html#cancellation-is-cooperative

    Uma coroutine precisa ser cooperativa para ser cancelada. Toda a suspenf function in kotlin.coroutines sao cancelaveis.

    Ao cancelar uma coroutine, checa-se e lança-se CancellationException. Entretanto, se uma coroutine esta trabalhando

    private fun task() = runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5) { // computation loop, just wastes CPU
                // print a message twice a second
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
            println("After task")
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // cancels the job and waits for its completion
        println("main: Now I can quit.")
    }

 */


private fun executeTask(backgroundTask: (CoroutineScope) -> Unit) = runBlocking {
    val job = launch(Dispatchers.Default) {
        backgroundTask(this)
    }
    delay(2000L)
    println("Cancelando ...")
    //job.cancel()
    /*
      https://kotlinlang.org/docs/cancellation-and-timeouts.html#cancellation-is-cooperative
      " Se a a coroutine estiver executando uma operacao e nao validar se tal computacao foi cancelada
       entao a coroutine nao sera cancelada. Note que no exemplo usado nesse arquivo, temos um loop
       que vai de 0 ate um inteiro grande, e ao cancelar a coroutine após alguns segundos de executa-la
       o looping continua da mesma forma"

    */
    job.cancelAndJoin()
    println("Cancelado - IsCompleted ? ${job.isCompleted}")
}

private fun tryCancelTask() {
    executeTask {
        var nextTime = System.currentTimeMillis()
        var idx = 0
        while (idx < 65000000) {
            if (System.currentTimeMillis() >= nextTime) {
                println("I'm alive $nextTime")
                println("Coroutine: $it -> ${dtf.format(LocalDateTime.now())} $idx")
                nextTime += 500
            }

            it.coroutineContext.job.let {
                    job ->
                if (job.isCompleted) {
                    println("Job $job, completed")
                }
                if (job.isCancelled) {
                    println("Job $job, canceled")
                }
            }

            if (!it.coroutineContext.job.isActive) {
                println("Job ${it.coroutineContext.job}, inactive - $idx")
            }

            //if (!it.isActive)
                //break

            idx += 1
        }
        println("After Background Task: $idx")
    }
}


fun main() {
    tryCancelTask()
}