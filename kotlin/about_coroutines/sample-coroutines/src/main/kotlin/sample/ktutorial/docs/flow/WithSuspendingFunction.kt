package sample.ktutorial.docs.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


/**
 * https://kotlinlang.org/docs/flow.html#suspending-functions
 * Esse exemplo bloqueia a MainThread. Podemos marcar a funcao com o modifiicador
 * suspend e assim ela pode excuar de forma mais eficiente sem o efeito bloqueante da Thread
 * */
suspend fun computation(): List<Int> {
    delay(1000) // delay que simula uma operacao custosa
    return listOf(1, 2, 3)
}


suspend fun computationWithSequence() = sequence {
    for (i in 0..200) {
        Thread.sleep(100)
        yield(i)
    }
}

private fun checkComputation() = runBlocking {
    computation().forEach {
        println(it)
    }
}


private fun checkComputationWithSequence() = runBlocking {
    computationWithSequence().drop(100).forEach {
        println(it)
    }
}


fun main() {
    checkComputationWithSequence()
}