package sample.ktutorial.docs.flow.builders

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import sample.ktutorial.logCoroutineScope

/*
    https://kotlinlang.org/docs/flow.html#flows-are-cold
    Flows sao definidos segundo a documentacao como COld Streams assim como
    Sequeses
 */

fun simple() : Flow<Int> = flow {
    println("Flow started")
    logCoroutineScope("Simple function")
    for (i in 1 .. 3) {
        kotlinx.coroutines.delay(100)
        emit(i)
    }
}

private fun checkSimple() = runBlocking {
    println("Iniciando simple function")
    logCoroutineScope("CheckSimple function")
    val flow  = simple()
    println("Iniciando collect ...")
    flow.collect {
        value ->  println(value)
    }
    println("Iniciando collect de novo ")
    flow.collect {
        value -> println(value)
    }
}

fun main() {
    checkSimple()
}