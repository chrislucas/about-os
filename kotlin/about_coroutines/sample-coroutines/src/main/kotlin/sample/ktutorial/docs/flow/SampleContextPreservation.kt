package sample.ktutorial.docs.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import sample.ktutorial.logCoroutineScope

/*
    https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html
    Context preservation

    O flow possui uma propriedade de presernvacao de contexto
        - Encapsula seu proprio contexto de execucao e nunca o propaga
        - Tornando trivial o entendimento sobre o contexto de execução de operacoes
        de transformacoes ou de terminais
 */

/*
    O existe uma forma de mudar o contexto de um flow: Usando o operador flowON

    - FlowOn: Operador que muda o contexto de upstream
 */

val ctxA = Dispatchers.Unconfined
val sampleFlowA = (1..100).asFlow()
    .map { Pair(it, it and 1 == 0) }    // upstream - isso eh executado no contexto ctxA
    .flowOn(ctxA)   // muda o contexto de upstream: flowOf e map


private fun checkFlowOn() = runBlocking {
    println(sampleFlowA)
    // downstream
    /*
    sampleFlowA.collect {
        println(it)
    }
     */

    println("***************************************************")

    val filtered = sampleFlowA          // ctxA est encapsulado nesse flow
        .filter { it.first == 10 }       //

    withContext(Dispatchers.IO) {
        /*
            Toda operacao nao encapsulada no contexto ctxA sera executada agora
            no contexto IO
         */
        println(filtered.singleOrNull())
    }

    withContext(ctxA) {
        println(filtered.singleOrNull())
    }
}


/*
    Do ponto de vista de implementacao, toda implementacao de flow
    deve somente ser emitida a partir da mesma coroutine.

    Essa restricao eh eficientemente forçada a partir da funcao
    padrao de construcao do flow: flow {}
 */

private val checkProhibitedOperation = flow {

    val thread = logCoroutineScope("flow")

    // proibido
    val jobB = GlobalScope.launch {
        val threadB = logCoroutineScope("GlobalScope launch 42")
        println(threadB)
        println(threadB == thread)
    }
    println(jobB)
    println("************************************************")

    val jobC = CoroutineScope(Dispatchers.Unconfined).launch {
        val threadC = logCoroutineScope("Unconfined launch 42")
        println(threadC)
        println(threadC == thread)
    }
    println(jobC)
    println("************************************************")


    withContext(CoroutineName("Simple")) {
        val threadD = logCoroutineScope("CoroutineName launch 42")
        println(threadD)
        println(threadD == thread)
        println("************************************************")
    }


    emit("emit 42")

    coroutineScope {
        val threadE = logCoroutineScope("CoroutineScope function")
        emit("emit 43")
        println(threadE == thread)
    }
}

private fun check() = runBlocking {
    checkProhibitedOperation.collect {
        println(it)
    }
}

fun main() {
    check()
}