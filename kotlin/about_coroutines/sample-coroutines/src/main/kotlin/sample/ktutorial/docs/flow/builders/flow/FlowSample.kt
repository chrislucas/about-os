package sample.ktutorial.docs.flow.builders.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
    https://kotlinlang.org/docs/flow.html#flows

    interface Flow<out T>
    https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html


    Uma estrutura de dados de stream assicrona qye sequencialmente EMITE valores
        - esses eventos ou sao completados normalmente o lancam uma exception


    https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html

    Flow Builders

    flowOf(...)

 */


suspend fun mockComputation() = flow {
    for (i in 0..200) {
        delay(500)
        emit(i)
    }
}

suspend fun computing() = flow {
    for (i in 0..200) {
        delay(500)
        emit("Iteracao $i da funcao computing - $this")
    }
}

private fun fn() = runBlocking {
    launch {
        for (i in 0..200) {
            println("Iteracao $i da funcao fn - $this")
            delay(500)
        }
    }
    // collect o FLOW
    computing().collect { println(it) }
}


fun main() {
    fn()
}