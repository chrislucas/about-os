package sample.ktutorial.docs.flow.builders.flow.operator.terminal.launchin

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import sample.ktutorial.docs.flow.builders.flow.mockComputation


/*
    https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/launch-in.html

    # Terminal Flow Operator

    - Lança a Colecao de um dado Flow num escopp
        - isso eh uma forma simplificada de se escrever
            scope: CoroutineScope
            scope.launch {}

    - Eh comum utilizar esse operador com outras funcoes/operadores para processar todos os valores emitidos e
    lidar com uma excecao durante a emicao ou processamento
        - onEach, onCompletion, catch

 */

private fun check() = runBlocking {
    val job = mockComputation()
        .onEach { println(it) }
        .onCompletion { println("Finish") }
        .catch { error("Deu Ruim") }
        .launchIn(CoroutineScope(Dispatchers.IO))
    println("JOB: $job")
    //job.join()
    delay(500)
    job.cancel("Finish")
}


fun main() {
    check()
}