package sample.ktutorial.docs.cancelations

import kotlinx.coroutines.*
import sample.ktutorial.docs.basics.scope.concurrency.dtf
import java.time.LocalDateTime

/*
    https://kotlinlang.org/docs/cancellation-and-timeouts.html#making-computation-code-cancellable


    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (isActive) { // cancellable computation loop
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }

    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")

 */


private fun call(backgroundTask: (CoroutineScope) -> Unit) = runBlocking {
    val job = launch {
       // backgroundTask(this)
        var idx = 0
        while (idx < 4400000) {
            if (!isActive)
                break
            println("${idx++}")
        }
        println("After: $idx")
    }

    delay(1000L)
    println("Cancelando ...")
    job.cancelAndJoin()
    println("Cancelado - IsCompleted ? ${job.isCompleted}")
}


private fun checkCallBackgroundTask() {
    call {
        var counter = 0
        while (it.isActive) {
            println("Job: ${it.coroutineContext.job}")
            println("Coroutine: $it -> ${dtf.format(LocalDateTime.now())} | Counter: ${counter++}")
        }
        println("After coroutines execution: Counter -$counter")
    }
}


fun main() {
    checkCallBackgroundTask()
}