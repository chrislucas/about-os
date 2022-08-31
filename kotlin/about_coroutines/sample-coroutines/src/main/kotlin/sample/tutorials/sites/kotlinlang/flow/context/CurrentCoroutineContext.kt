package sample.tutorials.sites.kotlinlang.flow.context

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

/**
https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/current-coroutine-context.html

currentCoroutineContext()
 * Retornar a instancia de CoroutineContext recuperada pelo atributo coroutineContext
 * @see kotlin.coroutines.coroutineContext

 */


fun main() {
    runBlocking {
        val flowInt = flow {
            emit(1)
            println(
                "Inside RunBlocking | Coroutine CTX: $coroutineContext | " +
                        "Current Coroutine CTX: ${currentCoroutineContext()} "
            )
            /**
                currentCoroutineContext()
                retorna o contexto atual onde o FLOW está coletando
                Essa funcao eh um alias para evitar colisãp com o nome coroutineContext
             * @see coroutineContext
             */
        }

        flowInt.collect(::println)

        withContext(Dispatchers.IO) {
            val flowInt2 = flow {
                println(
                    "FlowInt2 - Inside WithContext(Dispatchers.IO) | Coroutine CTX: $coroutineContext | " +
                            "Current Coroutine CTX: ${currentCoroutineContext()} "
                )
                emit(2)
            }

            flowInt2.collect(::println)

            val flowInt3 = flow {
                println(
                    "FlowInt3 - Inside WithContext(Dispatchers.IO) | Coroutine CTX: $coroutineContext | " +
                            "Current Coroutine CTX: ${currentCoroutineContext()} " +
                            "Mudando o contexto usando: 'lowOn(ctx)'"
                )
                emit(3)
            }.flowOn(Dispatchers.Default)

            flowInt3.collect(::println)
        }

        val flowInt4 = flow {
            println(
                "FlowInt4 - Inside Runblocking | Coroutine CTX: $coroutineContext | " +
                        "Current Coroutine CTX: ${currentCoroutineContext()} " +
                        "Mudando o contexto usando: 'lowOn(ctx)'"
            )
            emit(4)
        }.flowOn(Dispatchers.Unconfined)

        flowInt4.collect(::println)
    }
}