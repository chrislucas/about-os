package sample.book.kcoroutines.tutorials.suspendfunworks

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume

private val executor = Executors.newSingleThreadScheduledExecutor {
    Thread(it, "scheduler").apply { isDaemon = true }
}

abstract class OnCancellableContinuation {
    abstract fun beforeSuspend()

    abstract fun afterResume()

    fun <T> cancel(cancellableContinuation: CancellableContinuation<T>): Boolean =
        cancellableContinuation.cancel(Throwable("Error"))

    fun onCancellableContinuation(error: Throwable?) {
        error?.let { println(it) }
    }
}

class DefaultCancellableContinuation : OnCancellableContinuation() {
    override fun beforeSuspend() {
        println("Before suspend")
    }

    override fun afterResume() {
        println("After resume")
    }
}


@OptIn(ExperimentalCoroutinesApi::class)
private suspend fun checkExperimentalCancellableCoroutine(
    onCancellableContinuation: OnCancellableContinuation,
    task: (OnCancellableContinuation, CancellableContinuation<*>) -> Unit
) {
    println("Antes de executar suspendCancellableCoroutine")
    suspendCancellableCoroutine<Unit> {
        onCancellableContinuation.beforeSuspend()
        executor.schedule(
            {
                task(onCancellableContinuation, it)
                it.resume(Unit, onCancellableContinuation::onCancellableContinuation)
            }, 4000L, TimeUnit.MILLISECONDS
        )
        onCancellableContinuation.afterResume()
    }
    println("Depois de executar suspendCancellableCoroutine")
}


suspend fun main() {
    checkExperimentalCancellableCoroutine(DefaultCancellableContinuation()) { callback, continuation ->
        callback.cancel(continuation)
    }
}