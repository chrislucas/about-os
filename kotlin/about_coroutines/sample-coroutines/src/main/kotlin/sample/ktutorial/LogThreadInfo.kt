package sample.ktutorial

import kotlinx.coroutines.currentCoroutineContext

suspend fun logCoroutineScope(message: String): Thread =
    Thread.currentThread().apply {
        println(
            String.format(
                "Thread Name: [%s]\nThreadRef: [%s]\nThread ContextClassLoader[%s]\n" +
                        "CurrentCoroutineContext: %s\nMessage: [%s]\n",
                name,
                this,
                contextClassLoader,
                currentCoroutineContext(),
                message
            )
        )
    }


fun logThreadScope(message: String): Thread =
    Thread.currentThread().apply {
        println(String.format("Thread Name: [%s]\nMessage: [%s]\n", name, message))
    }
