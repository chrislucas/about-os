package sample.ktutorial.docs

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.collect

/*
    https://kotlinlang.org/docs/flow.html#flows

     Uma funcao que retornar um List<Int> o retorna de uma unica vez

    "To represent the stream of values that are being asynchronously computed, we can use a  Flow<Int>"

    A diferença entre a funcao builder flow {} e sequence {} e simplesmente que a primeira funciona de forma
    assincrona e nao bloquea a mainthread a segunda eh o exato oposto
 */
private fun countWithFlow() = flow {
    for (i in 0..10) {
        delay(400L)
        emit("Flow $i")
    }
}


private fun improveDelay(time: Long = 1000L) = CoroutineScope(Dispatchers.IO).launch { delay(1000L) }


private fun countWithSequence() = sequence {
    for (i in 0..10) {
        Thread.sleep(400L)
        yield("Sequence $i")
    }
}


private fun CoroutineScope.fakeCompute() =
    launch {
        for (i in 0..10) {
            println("Checking Thread with flow $i")
            delay(1000L)
        }
    }

private suspend fun anotherFakeComputing() {
    for (i in 0..10) {
        println("Checking Thread with flow $i")
        delay(1000L)
    }
}

private fun checkMainThreadWithFlow() = runBlocking {
    /*
    launch {
        for (i in 0..10) {
            println("Checking Thread with flow $i")
            delay(1000L)
        }
    }
     */

    //fakeCompute()
    //anotherFakeComputing()

    launch {
        anotherFakeComputing()
    }

    countWithFlow().collect { println(it) }
}

private fun checkMainThreadWithSequence() = runBlocking {
    /*
    launch {
        for (i in 0..10) {
            println("Checking Thread with flow $i")
            delay(1000L)
        }
    }
     */
    //fakeCompute()
    anotherFakeComputing()

    countWithSequence().forEach {
        println(it)
    }
}


fun main() {
    //checkMainThreadWithFlow()
    checkMainThreadWithSequence()
}