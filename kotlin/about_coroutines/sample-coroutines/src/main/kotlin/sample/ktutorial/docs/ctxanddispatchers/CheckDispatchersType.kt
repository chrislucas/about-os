package sample.ktutorial.docs.ctxanddispatchers

import kotlinx.coroutines.*
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

// https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html

/*
    CoroutineContext
    https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/#coroutinecontext
    - Contexto persistente para coroutines
    - Uma estrutura de dados set
 */


/*
    O metodo newSingleThreadContext(String) faz parte de uma api obsoleta que sera substituida, segundo a
    documentacao encontrada no proprio codigo fonte da API

    CopyDoc

    Creates a coroutine execution context using a single thread with built-in yield support.
    NOTE: The resulting ExecutorCoroutineDispatcher owns native resources (its thread).

     Resources are reclaimed by ExecutorCoroutineDispatcher.close.
    If the resulting dispatcher is closed and attempt to submit a continuation task is made,
    then the Job of the affected task is cancelled and the task is submitted to the Dispatchers.IO,
    so that the affected coroutine can cleanup its resources and promptly complete.

    NOTE: This API will be replaced in the future. A different API to create thread-limited thread pools that is based on
    a shared thread-pool and does not require the resulting dispatcher to be explicitly closed will be provided,
    thus avoiding potential thread leaks and also significantly improving performance,
    due to coroutine-oriented scheduling policy and thread-switch minimization. See issue #261  for details.
    If you need a completely separate thread-pool with scheduling policy that is based on the standard JDK executors,
    use the following expression: Executors.newSingleThreadExecutor().asCoroutineDispatcher().

    See Executor.asCoroutineDispatcher for details.

    Params:
    name - the base name of the created thread
 */

/**
 * @see java.util.concurrent.ExecutorService
 * @see kotlinx.coroutines.asCoroutineDispatcher
 * @see kotlinx.coroutines.newSingleThreadContext -> Obsoleto
 * @see ExecutorCoroutineDispatcher
 */
val executorCoroutineDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

@OptIn(ObsoleteCoroutinesApi::class)
private fun checkDispatchers() = runBlocking(executorCoroutineDispatcher) {
    /**
            Quando launch nao utiliza parametros ela herda o contexto da instancia de
            CoroutineScope que criou a coroutine, no caso do exemplo aqui estamos
            falando do contexto da coroutine construida pelo metodo runBlocking
            que eh executado na Main Thread

        * @see CoroutineScope
            CoroutineScope
            https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html
            Define um escopo para uma coroutine. Tod0  o metodo construtor de coroutiner
            (launch, asymc, runBlocking) é uma extensao de CoroutineScope e herda o atributo
            coroutineContext para prograpagar automaticamente todos seus elementos e meios de cancelamento

             A melhor forma de obter uma instancia autonoma de escopo de coroutine é através
             das funcoes FACTORY CoroutineScope() e MainScope()

                - Devemos ter o cuidado de cancelar esses escopos quando nao forem mais necessarios

        * @see CoroutineContext
            CoroutineContext
            https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/coroutine-context.html



            Dispatchers.Unconfined
            https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-unconfined.html
     */
    launch {
        println("Launch Without param: Current Thread Name - ${Thread.currentThread().name}")
    }

    launch(context = Dispatchers.Unconfined) {
        println("Context Unconfined: Current Thread Name - ${Thread.currentThread().name}")
    }

    launch(context = Dispatchers.Default) {
        println("Context Default: Current Thread Name - ${Thread.currentThread().name}")
    }

    launch(context = Dispatchers.IO) {
        println("Context IO: Current Thread Name - ${Thread.currentThread().name}")
    }

    launch(context = EmptyCoroutineContext) {
        println("Context EmptyCoroutineContext: Current Thread Name - ${Thread.currentThread().name}")
    }

    launch(context = newSingleThreadContext("OwnSingleThreadContext")) {
        println("Context fun newSingleThreadContext(name): Current Thread Name - ${Thread.currentThread().name}")
    }
}


fun main() {
    checkDispatchers()
}