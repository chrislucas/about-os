package sample.tutorials.sites.kotlinlang.flow.sharedflow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import sample.ktutorial.logCoroutineScope


/*
    SharedFlow
    https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-shared-flow/

    ShareIn
    https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/share-in.html
 */

private suspend fun checkShareIn() {
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

    filtered.shareIn(CoroutineScope(Dispatchers.Unconfined), SharingStarted.Lazily).collect(::println)

}


fun main() {
    runBlocking {
        checkShareIn()
    }
}