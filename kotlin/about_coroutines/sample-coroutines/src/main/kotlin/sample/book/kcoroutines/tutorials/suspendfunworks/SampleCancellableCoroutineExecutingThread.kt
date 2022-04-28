package sample.book.kcoroutines.tutorials.suspendfunworks

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume


private fun continuationAfterSeconds(millis: Long, continuation: Continuation<Unit>) {
    thread {
        println("Suspenso $continuation")
        Thread.sleep(millis)
        continuation.resume(Unit)
        println("Retornado $continuation")
        println("Ainda executando a Thread 1")
        println("Ainda executando a Thread 2")
        println("Ainda executando a Thread 3")
        println("Ainda executando a Thread FIM")
    }
}

private suspend fun call() {
    println("Antes de executar suspendCancellableCoroutine")
    suspendCancellableCoroutine<Unit> {
        continuationAfterSeconds(1000L, it)
    }
    println("Depois de executar suspendCancellableCoroutine")
}

suspend fun main() {
    call()
}