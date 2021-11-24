package sample.ktutorial.docs.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import sample.ktutorial.logCoroutineScope

/*
    https://kotlinlang.org/docs/flow.html#making-busy-flow-cancellable

    No caso de ter um loop com uma operacao complexa e demora devemos
    explicitante checar por cancelamentos

    Flow and Reactive Streams
    https://kotlinlang.org/docs/flow.html#flow-and-reactive-streams

    A semelhanca entre a api Flow e RxJava por exemplo nao eh mera coincidencia, esse design da api
    foi inspirado pelo padrao Reactive Stream e suas implementacoes

    - o principal objetivo no design do Flow foi ser tao simples quanto possivel
    - ser compativel com suspend function e respeitar a estrutura de concorrencia


 */


private fun fakeBusyOperation() = flow {
    for (i in 0..100) {
        kotlinx.coroutines.delay(1000L)
        emit(i)
    }
}


private fun checkFakeBusyOperation() = runBlocking {
    fakeBusyOperation()
        .onEach { currentCoroutineContext().ensureActive() }
        .collect {
            if(it == 5)
                cancel("Cancelling")
            println(it)
        }
}

/*
    podemos usar a funcao/operador cancellable para nao precisar fazer a checagem
    de que a coroutine esta ativa
 */
private fun checkCancellableFlow() = runBlocking {
    (1 .. 100).asFlow()
        .onEach { delay(1000L) }
        .cancellable()
        .collect {
            if(it == 5)
                cancel("Cancelling")
            logCoroutineScope("$it")
        }
}


fun main() {
    //checkFakeBusyOperation()
    checkCancellableFlow()
}