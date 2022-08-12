package sample.ktutorial.docs.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import sample.ktutorial.logCoroutineScope

/**
 * https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html
 *
 * O flow tem uma propriedade de preservar o contexto. Encapsula o proprio contexto de execucao e nunca propaga
 * ou vaza para o fluxo.
 * Isso torna trivial a ideia sobre o contexto de exeucao de operacoes de transformacao ou operacoes terminais.
 *
 * So existe uma forma de mudar o contexto de um flow, atraves do operador flowON(context) que muda o contexto
 * upstream (contexto das operacoes que vieram antes do da chamada ao flowOn() )
 */


suspend fun checkContextPreservation() {
    val ctx = Dispatchers.IO
    val ints = flowOf(1, 2, 4)
        .map { it + 1 }         // sera executado no contexto IO
        .flowOn(ctx) // mudando o contexto do flow

    val filtered = ints // flow com o contexto Dispatcher.IO
        .filter { it == 3 }

    logCoroutineScope("Flow: $filtered")

    println("***********************************")

    withContext(Dispatchers.Default) {
        logCoroutineScope("Flow: $filtered")
        logCoroutineScope("Data: ${filtered.single()}")
    }
}


private suspend fun emmitFromTheSameCoroutine() {
    val IntFlow = flow {
        logCoroutineScope("Emit inside flow Scope")
        emit(1)
        coroutineScope {
            logCoroutineScope("Emit inside coroutineScopre")
            emit(2)
        }

/*

Exception in thread "main" java.lang.IllegalStateException: Flow invariant is violated:
		Emission from another coroutine is detected.
		Child of BlockingCoroutine{Active}@5e91993f, expected child of BlockingCoroutine{Active}@2b05039f.
		FlowCollector is not thread-safe and concurrent emissions are prohibited.
		To mitigate this restriction please use 'channelFlow' builder instead of 'flow'

        runBlocking {
            logCoroutineScope("Emit inside runBlocking")
            emit(3)
        }

 */

        // Nao vai emiter por se tratar de outra coroutine
        CoroutineScope(Dispatchers.Default).launch {
            logCoroutineScope("Emit inside CoroutineScope(Dispatchers.Default) - $this")
            emit(5)
        }

        // Nao vai emiter por se tratar de outra coroutine
        CoroutineScope(Dispatchers.IO).launch {
            logCoroutineScope("Emit inside CoroutineScope(Dispatchers.IO) - $this")
            emit(6)
        }

        // Nao vai emiter por se tratar de outra coroutine
        GlobalScope.launch {
            logCoroutineScope("Emit inside GlobalScope.launch")
            emit(7)

            launch(Dispatchers.IO){
                logCoroutineScope("Emit inside launch(IO) $this")
                emit(8)
            }

            withContext(CoroutineName("InsideGlobalScope")) {
                logCoroutineScope("Emit inside withContext CoroutineName")
                emit(9)
            }

            withContext(Dispatchers.IO) {
                logCoroutineScope("Emit inside withContext Dispatchers.Unconfined")
                emit(10)
            }
        }
    }

    IntFlow.collect {
        println("Collect $it")
    }
}

private suspend fun checkEnnitValueInsideRunBlocking() {

    val IntFlow = flow {
        emit(1)

        /*
            Ao tentar emitir algo dentro de um Runblocking

            Exception in thread "main" java.lang.IllegalStateException: Flow invariant is violated:
            Emission from another coroutine is detected.
            Child of BlockingCoroutine{Active}@6aa8ceb6, expected child of BlockingCoroutine{Active}@2530c12.
            FlowCollector is not thread-safe and concurrent emissions are prohibited.
            To mitigate this restriction please use 'channelFlow' builder instead of 'flow'
         */
        runBlocking {
            //emit(2)
            //emit(3)
            //emit(4)
            logCoroutineScope("$this")
        }
    }

    IntFlow.collect {
        println("Collect $it")
    }
}


suspend fun main() {
    runBlocking {
        //checkContextPreservation()
        //emmitFromTheSameCoroutine()
        checkEnnitValueInsideRunBlocking()
    }
}