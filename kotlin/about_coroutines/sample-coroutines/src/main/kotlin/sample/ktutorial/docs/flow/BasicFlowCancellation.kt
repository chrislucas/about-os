package sample.ktutorial.docs.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

/*
    https://kotlinlang.org/docs/flow.html#flow-cancellation-basics

    A implementacao de Flow adere ao comportamento de cancelação cooperativa de coroutines..
    A coleta (feita pela funcao collect) dos dados de um Flow pode ser cancelada quando
    o Flow em questao eh suspens0o numa suspend function cancelavel como a funcao delay.

    O exemplo abaixo mostra como cancelar um flow que esta sendo coletado.
    Ele será coletado dentro do bloco withTimeoutOrNull

 */


private fun simpleExecutionFlow() = flow {
    for (i in 0..10) {
        kotlinx.coroutines.delay(100L)
        emit(i)
    }
}


private fun checkSimpleExecutionFlow() = runBlocking {
    withTimeoutOrNull(850) {
        simpleExecutionFlow().collect {
            println(it)
        }
    }
    println("Done")
}

private fun sample() = runBlocking {
    /*
        Runs a given suspending block of code inside a coroutine with a
        specified timeout and returns null if this timeout was exceeded.

        Se o tempo de execucao da funcao exceder o tempo de timeout, withTimeoutOnNull
        retornar null
     */

    /*
        o curioso eh que fazendo as contas, se o intervalo for de dez numeros e o delay
        de 50ms, o tempo gasto seria de aproximadamente 500ms, mas o qualquer valor
        acima de 500 e abaixo de 550 para variavel timeLimit nao eh o suficiente para
        que withTimeoutOrNull retorne o valor 42 sempre. Acontece um comportamento
        inconsistente
     */
    val timeLimit = 545L
    val interval = 0 .. 9   // 10 numero no intervalo
    val timeDelay = 50L     // um delayt de 50 ms. Isso vezes dez items vai exceder o tempo limite

    val r = withTimeoutOrNull(timeLimit) {
        flow<String?> {
            for (i in interval) {
                delay(timeDelay)
                if (i == 4) {
                    emit(null)
                }
                emit("$i:$i")
            }
        }.collect {
            println(it)
        }
        42
    }

    println("Done $r")
}


fun main() {
    sample()
}