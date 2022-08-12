package sample.ktutorial.docs.flow.builders.flowon

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import sample.ktutorial.logCoroutineScope

/*
    https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/flow-on.html
    Muda o contexto onde o Flow esta sendo executado.
    Esse OPERADOR é composable e afeta todos os operadores precedentes que nao tem o seu proprio contexto
    Ainda, esse operador preserva o contexto: O contexto nao vaza para o downstream flow
 */

val seqIntFlow = flow {
    for (i in 0..100) {
        emit(i)
    }
}.flowOn(Dispatchers.Unconfined)


fun checkContextFlow(): Flow<Int> =
    seqIntFlow.map { it * 2 }
        .flowOn(Dispatchers.IO)
        .filter { it % 2 == 0 }
        .flowOn(Dispatchers.Default)


/**
 * https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/share-in.html
 * ShareIn
 *
 * Transformando cold Flow into hot SharedFlow
 */

fun checkShareIn() {
    val context = CoroutineScope(Dispatchers.Unconfined)
    //seqIntFlow.shareIn(context)
}

private fun checkContext() {
    runBlocking {
        withContext(Dispatchers.Default) {
            logCoroutineScope("Dispatcher.Default - $this")
            //seqIntFlow.collect { println(it) }
        }

        withContext(Dispatchers.IO) {
            logCoroutineScope("Dispatcher.IO - $this")
        }

        withContext(Dispatchers.Unconfined) {
            logCoroutineScope("Dispatcher.Unconfined - $this")
        }
    }
}

suspend fun main() {
    runBlocking {
        checkContextFlow().collect { println(it) }
    }
}