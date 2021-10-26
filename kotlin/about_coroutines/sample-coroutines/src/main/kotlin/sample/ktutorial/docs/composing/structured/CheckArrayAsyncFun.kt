package sample.ktutorial.docs.composing.structured

import kotlinx.coroutines.*
import sample.ktutorial.docs.basics.scope.concurrency.coroutineScopeIO
import sample.ktutorial.docs.basics.scope.concurrency.localDateTimeNow
import java.lang.StringBuilder

private fun checkTasks() {
    val tasks = arrayOf<Deferred<Any>>(
        coroutineScopeIO.async {
            delay(1000L)
            println("Iniciado async 1 $localDateTimeNow")
            delay(2000L)
            println("terminado async 1 $localDateTimeNow")
        },

        coroutineScopeIO.async {
            delay(1000L)
            println("Iniciado async 2 $localDateTimeNow")
            delay(2000L)
            println("terminado async 2 $localDateTimeNow")
        },

        coroutineScopeIO.async {
            delay(500L)
            println("Iniciado async 3 $localDateTimeNow")
            delay(500L)
            println("terminado async 3 $localDateTimeNow")
        },


        coroutineScopeIO.async {
            delay(5500L)
            println("Iniciado async 4 $localDateTimeNow")
            delay(1500L)
            println("terminado async 4 $localDateTimeNow")
        },

        coroutineScopeIO.async {
            println("Iniciado async 5 $localDateTimeNow")
            delay(1500L)
            println("terminado async 4 $localDateTimeNow")
        },
    )

    for (task in tasks) {
        coroutineScopeIO.launch {
            task.await()
        }
    }

    do {
        val message = StringBuilder()
        for (i in tasks.indices) {
            val data = tasks[i].run {
                //"Task - Key: ${this.key}, IsCompleted: ${this.isCompleted}, Job ${this.job}, ref: $this"
                "Task - Key:${this.key}, [IsCompleted:${this.isCompleted}, " +
                        "IsActive:${this.isActive}, IsCanceled:${this.isCancelled}], Job:$job, JobKey:${job.key}"
            }
            message.append(if (i == 0) data else "\n$data")
        }
        println("$message\n$localDateTimeNow\n")
    } while (tasks.any { it.isActive })
}


fun main() {
    checkTasks()
}