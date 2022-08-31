package sample.ktutorial

import kotlinx.coroutines.currentCoroutineContext

suspend fun logCoroutineScope(message: String): Thread =
    Thread.currentThread().apply {
        println(
            String.format(
                "Thread [%s] | CurrentCoroutineContext: %s | Message: %s", name,
                currentCoroutineContext(), message
            )
        )
    }


fun logThreadScope(message: String): Thread =
    Thread.currentThread().apply {
        println(String.format("Thread [%s] | Message: %s", name, message))
    }
