package sample.tutorials.sites.raywenderlich.tutorial.chp3.jobs.child

import kotlinx.coroutines.*
import sample.ktutorial.logCoroutineScope

/*
    https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/children.html

    Codigo fonte da interface Job
    https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/common/src/Job.kt#L199
 */


// sample
/*
    Leia a discussao sobre o comportamento desse codigo e tente entender
    https://lightrun.com/answers/kotlin-kotlinx-coroutines-structured-concurrency-with-two-parent-jobs
 */

private fun checkSample() {
    fun testA() {
        runBlocking {
            val job = Job()
            logCoroutineScope("First job: $job")

            val mainJob = launch {
                val jobB = launch (context =  coroutineContext + job) {
                    while (isActive) {
                        logCoroutineScope("I'm alive")
                        delay(1000)
                    }
                }
                println("Job B: $jobB")
                jobB.join()
            }.also {
                println("MainJob job[1]: $it")
                it.invokeOnCompletion {
                    println("I'm dead")
                }
            }
            delay(5000)
            mainJob.cancel()
            println("Done - Main Job: $mainJob")
        }
    }

    fun testB() {
        runBlocking {
            val mainJob = launch {
                val jobB = launch (context =  coroutineContext) {
                    while (isActive) {
                        logCoroutineScope("I'm alive")
                        delay(1000)
                    }
                }
                println("Job B: $jobB")
                jobB.join()
            }.apply {
                println("MainJob job: $this")
                invokeOnCompletion {
                    println("I'm dead")
                }
            }
            delay(5000)
            mainJob.cancel()
            println("Done - Main Job: $mainJob")
        }
    }

    testB()
}


fun main() {
    checkSample()
}