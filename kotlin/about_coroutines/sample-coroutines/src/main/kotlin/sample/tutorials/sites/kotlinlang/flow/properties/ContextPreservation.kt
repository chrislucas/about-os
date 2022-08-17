package sample.tutorials.sites.kotlinlang.flow.properties

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import sample.ktutorial.logCoroutineScope
import kotlin.coroutines.CoroutineContext

/*
    https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/

    Flow constraints
            - Todas as implementacoes da interface Flow devem aderir a 2 propriedades chaves
            - Context preservation
            - Exception transparency

    Essas propriedades garantem a capacidade de implementar um raciocio logico sobre o codigo com flow
        - Permite modularizar o codigo de tal forma que os Emitters do UPSTREAM FLOW podem
        ser desenvolvidos separadamente dos DOWNSTREAM FLOW COLLECTORS

        - Um usuario do flow nao precisa estar ciente da detalhes de implementacao do
        UPSTREAM FLOW que ele está usando

 */

private suspend fun checkContextPreservarion1() {

    val scope = CoroutineScope(CoroutineName("Context A"))

    val fa = (1..100).asFlow()
        .flowOn(Dispatchers.IO)

    val filtered = fa.filter { it % 3 == 0 }

    withContext(Dispatchers.IO) {
        logCoroutineScope("Process Filtered flow: $filtered")
    }
}

private suspend fun checkContextPreservarion2() {
    val fa = (1..100)
        .asFlow() // flowOF(1, 2 .. 100)
        .map { it + 1 } // sera executado na coroutine com nome CTX_A
        .flowOn(CoroutineName("CTX_A")) // mudando o contexto da coroutine do flow

    logCoroutineScope("Flow: $fa")

    println("\n**********************************************************************\n")

    // CTX_A está encapsulado em 'fa'
    val filtered = fa.filter { it % 3 == 0 } // a operacao filter nao está em nenhum contexto


    withContext(Dispatchers.IO) {
        logCoroutineScope("Filtered Flow: $filtered | Scope: $this")
    }
}


/*
    Flow constraints
        Toda a implementacao da interface Flow deve aderir a 2 propriedades
        - Context Presernvation
        - Exception Transparency
    Essas propriedades garantem a capacidade de implementar de forma racional o codigo
    com flows de forma modularizada tal   que o UPSTREAM FlOW EMITTER possa ser desenvolvido separadamente
    do DOWNSTREAM FLOW COLLECTOR

    Context Preservantion
    - Flow encapsula o seu proprio contexto de execucacao, nao propagado-o e nem o vaza
 */

private suspend fun breakContextPreservation() {

    val fa = flow {

        logCoroutineScope("Dentro do flow: $this")
        emit(1)

        coroutineScope coroutine1@{
            logCoroutineScope("Dentro de uma Coroutine ${this@coroutine1} | Flow ${this@flow}")
            emit(2)
        }

        suspend {
            logCoroutineScope("Dentro de uma suspend function: $this")
            emit(3)
        }.invoke()


        suspend fun changeContextEmitAndGetError(ctx: CoroutineContext, value: Int) {
            /*
                Exception in thread "main" java.lang.IllegalStateException: Flow invariant is violated:
                Flow was collected in [BlockingCoroutine{Active}@d1ee4, BlockingEventLoop@74144d1],
                but emission happened in [DispatchedCoroutine{Active}@27275644, Dispatchers.IO].
                Please refer to 'flow' documentation or use 'flowOn' instead

                Do ponto de vista de implementacao, tod0 a implementacao de flow deve emitir um valor
                 da mesma coroutine/contexto que ele foi criado

             */
            withContext(ctx) {
                logCoroutineScope("Tentando emitir um valor do contexto: ${this.coroutineContext}")
                emit(value)
            }
        }


        suspend fun checkChangeContextAndEmitInsideFlow() {
            /*
                Nenhuma das chamadas abaixo irá funcionar e será lançada uma exception, pois o design de construcao
                do flow nao permite emitir nenhum valor por um outro contexto/coroutine
             */
            changeContextEmitAndGetError(CoroutineName("MyCotourine"), 4)
            changeContextEmitAndGetError(Dispatchers.IO, 4)
            changeContextEmitAndGetError(Dispatchers.Default, 4)
            changeContextEmitAndGetError(Dispatchers.Unconfined, 4)

            GlobalScope.launch(Dispatchers.IO) {
                emit(4)
            }
        }
        // checkChangeContextAndEmitInsideFlow()

        suspend fun emitInsideChangeContext() {
            withContext(Dispatchers.Default) {
                val f = flow {
                    logCoroutineScope("Flow Reference $this")
                    emit("Ola. Estou dentro da funcao WithContext")
                }
                f.collect(::println)
            }

            CoroutineScope(Dispatchers.IO).launch {
                val f = flow {
                    logCoroutineScope("Flow Reference $this")
                    emit("Ola. Estou dentro da funcao Launch")

                }
                f.collect(::println)
            }
        }
    }


    suspend fun exploreFlowOn() {
        with(fa) {
            // flowOn(Dispatchers.Unconfined)
            // flowOn(Dispatchers.IO)
            // flowOn(Dispatchers.Default)
        }
        fa.collect(::println)
    }

}

suspend fun checkEmitValueFromCurrentCoroutineContext() {
    val f = flow {
        suspend fun test() {
            val ctx = currentCoroutineContext()
            withContext(ctx) {
                val msg = String.format(
                    "Emitindo um valor a partir Contexto atual da " +
                            "coroutine criada para o flow: %s.\nCoroutineScope: %s | %s",
                    ctx,
                    this,
                    this.coroutineContext
                )
                logCoroutineScope(msg)
                emit(123)
            }
        }

        test()
        emit(1)
    }

    f.collect()

}


fun main() {
    runBlocking {
        //check()
        //breakContextPreservation()
        //checkContextPreservarion2()
        checkEmitValueFromCurrentCoroutineContext()
    }
}