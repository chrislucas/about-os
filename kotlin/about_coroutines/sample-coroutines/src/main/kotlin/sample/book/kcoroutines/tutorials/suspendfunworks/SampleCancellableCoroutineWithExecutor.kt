package sample.book.kcoroutines.tutorials.suspendfunworks

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume

private val executor = Executors.newSingleThreadScheduledExecutor {
    Thread(it, "scheduler").apply { isDaemon = true }
}

@OptIn(ExperimentalCoroutinesApi::class)
private suspend fun checkExperimentalCancellableCoroutine(onCancellation: (Throwable) -> Unit = {}) {
    println("Antes de executar suspendCancellableCoroutine")
    suspendCancellableCoroutine<Unit> {
        println("Antes: $it")
        executor.schedule(
            {
                it.resume(Unit, onCancellation)
            }, 1000L, TimeUnit.MILLISECONDS
        )
        println("Depois: $it")
    }
    println("Depois de executar suspendCancellableCoroutine")
}


private suspend fun checkResumeCoroutine() {
    println("Antes de executar suspendCancellableCoroutine")
    suspendCancellableCoroutine<Unit> {
        println("Antes: $it")
        executor.schedule(
            {
                it.resume(Unit)
            }, 1000L, TimeUnit.MILLISECONDS
        )
        println("Depois: $it")
    }
    println("Depois de executar suspendCancellableCoroutine")
}


suspend fun main() {
    checkResumeCoroutine()
}