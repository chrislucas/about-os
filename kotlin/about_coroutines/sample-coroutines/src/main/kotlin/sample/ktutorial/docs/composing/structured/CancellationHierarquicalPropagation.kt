package sample.ktutorial.docs.composing.structured

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.lang.ArithmeticException

/*
    https://kotlinlang.org/docs/composing-suspending-functions.html#structured-concurrency-with-async
 */


private suspend fun operationA(): Int {
    try {
        delay(4000L)   // simulando uma operacao longa
        return 42
    } catch (e: Exception) {
        throw Exception("$e operacao cancelada")
    }
}


private suspend fun operationB(): Int {
    println("Op: B")
    throw ArithmeticException("Erro na operacao B")
    //return 38
}

private suspend fun executeAsyncOp(): Pair<Int, Int> = coroutineScope {
    val p = async { operationA() }
    val q = async { operationB() }
    Pair(p.await(), q.await())
}

private suspend fun operationC(): Int {
    try {
        delay(4000L)   // simulando uma operacao longa
        return 42
    } finally {
        println("Funcao cancelada")
    }
}


fun main() {
    suspend fun checkWithRunCatching() {
        val rs = runCatching {
            executeAsyncOp()
        }
        println(rs)
        println(rs.getOrNull())
        println(rs.isSuccess)
    }

    suspend fun checkWithTryCatch() {
        try {
            executeAsyncOp()
        } catch (e: Exception) {
            println("executeAsyncOp Failed\n$e")
        }
    }

    runBlocking {
        checkWithRunCatching()
        println("*******************************************")
        checkWithTryCatch()
    }
}