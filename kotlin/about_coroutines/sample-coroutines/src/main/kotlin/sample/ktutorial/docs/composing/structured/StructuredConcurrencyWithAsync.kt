package sample.ktutorial.docs.composing.structured

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/*
    https://kotlinlang.org/docs/composing-suspending-functions.html#structured-concurrency-with-async

 */

suspend fun sampleAsyncSum(): Int = coroutineScope {

    suspend fun op1(): Int {
        delay(1000L)
        return 10
    }

    suspend fun op2(): Int {
        delay(2500L)
        return 15
    }

    /*
        Como async eh uma ext function de CoroutineScope ela precisa ser chamada dentro de um escopo
        de coroutine, como estamos fazendo agora
     */
    val rs1 = async { op1() }
    val rs2 = async { op2() }

    // Se uma excecao for lancada dentro do corpo dessa funcao, todas as coroutines
    // lancadas nesse escopo serao canceladas
    rs1.await() + rs2.await()
}

suspend fun asyncBinaryOpInt(p: Int, q: Int, op: suspend (Int, Int) -> Int): Int =
    coroutineScope {
        val result = async { op(p, q) }
        result.await()
    }


private fun checkAsyncBinaryOpInt() {
    runBlocking {
        val p = asyncBinaryOpInt(10, 20) { x, y ->
            x + y
        }
        println(p)
    }
}

private fun checkAsyncSum() = runBlocking {
    println(sampleAsyncSum())
}





fun main() {
    val timer = measureTimeMillis {
        checkAsyncSum()
    }
    println("In $timer ms.")
}