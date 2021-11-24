package sample.ktutorial.docs.flow.builders

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import sample.ktutorial.logCoroutineScope
import kotlin.coroutines.CoroutineContext

private fun simple(): Flow<Int> = flow {
    println("Flow started")
    logCoroutineScope("Simple function")
    for (i in 1..10) {
        delay(100)
        emit(i)
    }
}

// https://elizarov.medium.com/reactive-streams-and-kotlin-flows-bfd12772cda4

private fun <T> Flow<T>.onComplete(action: () -> Unit) = flow {
    collect { value -> emit(value) }
    action()
}

private suspend inline fun <T> Flow<T>.onFinish(crossinline action: suspend () -> Unit) = flow {
    collect { value -> emit(value) }
    action()
}

private fun checkOnCompletionInSimpleFlow() = runBlocking {
    simple()
        .onCompletion {
            logCoroutineScope("$this")
        }.collect {
            println(it)
        }
}


private fun checkOnCompleteExtFun() = runBlocking {
    simple().onComplete {
        println("onComplete")
        CoroutineScope(Dispatchers.Unconfined).launch {
            logCoroutineScope("$this")
        }
    }.collect {
        println(it)
    }
}

private fun checkOnFinish() = runBlocking {
    simple().onFinish {
        println("onFinish")
        logCoroutineScope("$this")
    }.collect {
        println(it)
    }
}


fun main() {
    checkOnCompletionInSimpleFlow()
    println("************************************************")
    checkOnCompleteExtFun()
    println("************************************************")
    checkOnFinish()
}