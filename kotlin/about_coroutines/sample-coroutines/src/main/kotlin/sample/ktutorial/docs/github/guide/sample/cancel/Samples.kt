package sample.ktutorial.docs.github.guide.sample.cancel

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * https://github.com/Kotlin/kotlinx.coroutines/tree/master/kotlinx-coroutines-core/jvm/test/guide
 */


private suspend fun provideJob(coroutineContext: CoroutineContext = Dispatchers.Default) =
    CoroutineScope(coroutineContext).launch {
        try {
            repeat(1000) {
                println("Exec $it")
                delay(500L)
            }
        } finally {
            withContext(NonCancellable) {
                println("Job running")
                delay(1000L)
                println("1 sec delayed")
            }
        }
    }


// https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/jvm/test/guide/example-cancel-06.kt
private fun cancel06() = runBlocking {
    val job = provideJob()
    delay(2500L)
    println("cancelling")
    // cancela o job e espera ate ser completado
    job.cancelAndJoin()
    println("canceled")
}

private fun cancel06V2() = runBlocking {
    val job = provideJob()
    delay(2500L)
    println("cancelling")
    // cancela o job e espera ate ser completado
    job.cancel("Cancel")
    println("canceled")
}

private fun cancel06V3() = runBlocking {
    val job = provideJob(Dispatchers.Unconfined)
    delay(2500L)
    println("cancelling")
    // cancela o job e espera ate ser completado
    job.cancelAndJoin()
    println("canceled")
}

fun main() {
    // cancel06()
    //cancel06V2()
    cancel06V3()
}