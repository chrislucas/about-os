package sample.ktutorial.docs.concurrency.sections.gapcoroutinesandthreads

import kotlinx.coroutines.*
import sample.ktutorial.docs.logCoroutineScope
import kotlin.coroutines.suspendCoroutine

/**
 * https://medium.com/androiddevelopers/bridging-the-gap-between-coroutines-jvm-threads-and-concurrency-problems-864e563bd7c
 */

suspend fun testSuspendableFunction() {
    val s = suspendCoroutine<String> {
        it.resumeWith(Result.success("Hello World"))
    }
    println(s)
}


private suspend fun dispatchertAsyncFun(dispatcher: CoroutineDispatcher = Dispatchers.Default) {
    logCoroutineScope("Antes de suspender 1")
    val s = CoroutineScope(dispatcher).async(start = CoroutineStart.LAZY) {
        testSuspendableFunction()
    }
    s.await()
    logCoroutineScope("Depois de suspender 1")
}

private suspend fun dispatchertLaunchFun(dispatcher: CoroutineDispatcher = Dispatchers.Default) {
    logCoroutineScope("Antes de suspender launch fun")
    CoroutineScope(dispatcher).launch(start = CoroutineStart.LAZY) {
        testSuspendableFunction()
    }
    logCoroutineScope("Depois de suspender launch fun")
}

private suspend fun dispatcherWithContextFun(dispatcher: CoroutineDispatcher = Dispatchers.Default) {
    logCoroutineScope("Antes de suspender 1")
    withContext(dispatcher) {
        testSuspendableFunction()
    }
    logCoroutineScope("Depois de suspender 1")
}

fun main() {
    runBlocking(CoroutineName("RunBlocking")) {
        dispatchertLaunchFun()
        dispatchertLaunchFun(Dispatchers.Unconfined)
    }
}