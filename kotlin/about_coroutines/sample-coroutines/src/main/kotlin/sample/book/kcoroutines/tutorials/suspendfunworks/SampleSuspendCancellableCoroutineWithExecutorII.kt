package sample.book.kcoroutines.tutorials.suspendfunworks

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * https://kt.academy/article/cc-suspension

 */
private val executor = Executors.newSingleThreadScheduledExecutor {
    Thread(it, "scheduler").apply { isDaemon = true }
}

interface SimpleDelayCallback<T> {
    fun after(continuation: Continuation<T>)
    fun before(continuation: Continuation<T>)
    fun resume(): T
}

/**
 * O Executor continua usando uma thread, mas é uma threda para todas as coroutines que chamarem
 * a funcao applySuspendDelay
 */
private suspend fun <T> applySuspendDelay(time: Long, callback: SimpleDelayCallback<T>): T =
    suspendCoroutine {
        callback.before(it)
        executor.schedule({
            it.resume(callback.resume())
        }, time, TimeUnit.MILLISECONDS)
        callback.after(it)
    }

private suspend fun checkCallDelay() {
    val callback = object : SimpleDelayCallback<Unit> {
        override fun after(continuation: Continuation<Unit>) {
            println("After suspend coroutine: $continuation")
        }

        override fun before(continuation: Continuation<Unit>) {
            println("Before suspend coroutine: $continuation")
        }

        override fun resume() = Unit
    }
    println("Start")
    applySuspendDelay(1000L, callback)
    println("End")
}


private fun checkDelayFromCoroutineApi() {
    runBlocking {
        print("hello ")
        delay(1000L)
        println(" world")
    }
}

private suspend fun checkSuspendCancellableCoroutine() {
    val s = suspendCancellableCoroutine<Int> { cancellableContinuation ->
        println("Before: $cancellableContinuation")
        cancellableContinuation.resume(1)
        println("After: $cancellableContinuation")
    }
    println(s)
}

suspend fun main() {
    checkSuspendCancellableCoroutine()
}
