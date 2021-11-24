package sample.ktutorial.docs.flow

import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import sample.ktutorial.logCoroutineScope

/*
    https://kotlinlang.org/docs/flow.html#flow-cancellation-checks

    A funcao builder flow executa uma garantia adicionar de checagem para cancelamento
    a cada vez que um valor eh emitido do fluxo atraves da funcao emit(Any)

    Essa checagem verifica se a coroutine nao foi cancelada através da extension fun

    ensureActive:
    https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/ensure-active.html

    "Segundo a doc: Garante que o escopo atual está ativo"

 */


private fun simpleSampleEmit() = flow {
    logCoroutineScope("SimpleSampleEmit fn")
    for (i in 0 .. 100) {
        emit(i)
    }
}


private fun checkSimpleSampleEmit() = runBlocking {
    simpleSampleEmit().collect {
        value ->
        if (value == 19)
            cancel("Cancelling...")
        println(value)
    }
}


private fun sampleWithRange() = runBlocking {
    (1 .. 100).asFlow().collect {
        logCoroutineScope("SampleWithRange fn")
        if (it == 10)
            cancel("Nice try...")
        println(it)
    }
}

fun main() {
    checkSimpleSampleEmit()
}