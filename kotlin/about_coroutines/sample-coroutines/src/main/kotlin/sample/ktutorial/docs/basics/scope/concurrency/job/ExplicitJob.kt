package sample.ktutorial.basics.scope.concurrency

import kotlinx.coroutines.*

/*
    https://kotlinlang.org/docs/coroutines-basics.html#an-explicit-job

    a funcao launch, responsavel por construir coroutines retornar uma instancia de Job, que eh um Handler
    para a coroutine lancada

 */


private fun checkExplicitJob() = runBlocking {
    coroutineScope {
        val job = launch {
            delay(1000L)
            println("World!")
        }

        println("Hello")
        // Docs/Commentario: Suspends the coroutine until this job is complete.
        job.join()
        println("Done")
    }
}

fun main() {
    checkExplicitJob()
}