package sample.book.kcoroutines.tutorials.suspendfunworks.resumingwithvaluew.feature.mockingrequestuser

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private suspend fun requestMockedUser() {
    val user = suspendCoroutine<User> { continuation ->
        println("Depois de suspender a coroutine")

        val async = CoroutineScope(continuation.context).async {
            doRequest { mockHttpRequestUser() }
        }

        CoroutineScope(continuation.context).launch {
            continuation.resume(async.await())
        }

        println("Depois de liberar a coroutine")
    }
    println(user)
}


private val executor = Executors.newSingleThreadScheduledExecutor {
    Thread(it, "scheduler").apply { isDaemon = true }
}

private suspend fun asynRequestUserwithExecutor() {
    val user = suspendCoroutine<User> { continuation ->
        println("Depois de suspender a coroutine")
        executor.execute {
            val async = CoroutineScope(continuation.context).async {
                doRequest { mockHttpRequestUser() }
            }

            CoroutineScope(continuation.context).launch {
                continuation.resume(async.await())
            }
        }
        println("Depois de liberar a coroutine")
    }
    println(user)
}

private suspend fun requestUserWithThread() {
    fun requestUser(callback: (User) -> Unit) {
        thread {
            Thread.sleep(3000L)
            callback(User("chrisluccas"))
        }
    }
    println("Antes")
    val user = suspendCoroutine<User> {
        requestUser { user -> it.resume(user) }
    }
    println("Depois")
    println(user)
}

private suspend fun requestUserWithExecutor() {
    fun requestUser(callback: (User) -> Unit) {
        executor.execute {
            callback(User("chrisluccas"))
        }
    }

    val user = suspendCoroutine<User> {
        requestUser {
            user -> it.resume(user)
        }
    }

    println(user)
}

suspend fun main() {
    requestUserWithThread()
    requestUserWithExecutor()
}