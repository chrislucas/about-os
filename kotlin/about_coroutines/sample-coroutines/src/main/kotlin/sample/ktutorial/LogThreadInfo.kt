package sample.ktutorial

import kotlinx.coroutines.currentCoroutineContext

suspend fun logCoroutineScope(message: String): Thread {
    val thread = Thread.currentThread()
    val coroutineContext = currentCoroutineContext()
    println(String.format("Thread [%s] | CurrentCoroutineContext: %s | Message: %s", thread.name, coroutineContext, message))
    return thread
}