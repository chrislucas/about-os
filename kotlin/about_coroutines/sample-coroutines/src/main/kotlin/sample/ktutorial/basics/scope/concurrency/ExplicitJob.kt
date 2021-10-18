package sample.ktutorial.basics.scope.concurrency

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
    https://kotlinlang.org/docs/coroutines-basics.html#an-explicit-job

 */


private fun checkExplicitJob() = runBlocking {
    coroutineScope {
        val job = launch {
            delay(1000L)
            println("World!")
        }

        println("Hello")
        job.join()
        println("Done")
    }
}

fun main() {
    checkExplicitJob()
}