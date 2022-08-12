package sample.ktutorial.docs.flow.callbackflow

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking

/**
 *  https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/callback-flow.html
 *
 *  - Cria uma instancia de colf-flow com elementos que sao enviados para o
 *  @see kotlinx.coroutines.channels.SendChannel, providenciado pelo bloco construtor via
 *  @see kotlinx.coroutines.channels.ProducerScope
 *  (https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-producer-scope/index.html)
 *
 *  - A funcao builder garante um ambiente thread-safey e a preservacao do contexto, assim
 *  ProducerScope pode ser usado de qualquer contexto. No exemplo abaixo usamos os metodos
 *  de ProducerScope no escopo de Callback.
 *
 *  O flow termina sua execucao quando o que estiver no bloco callbackFlow {} termina
 *
 *  O metodo awaitClose deve ser usado para manter o flow executando, do contrario o channel fechara
 *  assim que o bloco de codigo acabar.
 *
 *  O argumento passado para awaitClose eh chamado ou quando o consumor cancelar o flow ou quando
 *  callback-based API executar SendChannel.close
 *
 */

interface Callback<T> {
    fun onNextValue(value: T)
    fun onApiError(cause: Throwable)
    fun onCompleted(): Boolean
}

interface CallbaclBasedApi<T> {
    fun register(callback: Callback<T>)
    fun unregister(callback: Callback<T>)
}

fun <T> flowfrom(api: CallbaclBasedApi<T>): Flow<T> = callbackFlow {
    val callback = object : Callback<T> {
        override fun onNextValue(value: T) {
            trySendBlocking(value).onFailure { throwable ->
                println(throwable)
            }
        }

        override fun onApiError(cause: Throwable) {
            cancel(CancellationException("API ERROR", cause))
        }

        /**
         *
         * @see SendChannel: Sender's interface to Channel
         * https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-send-channel/index.html
         *
         * aqui channel é uma instancia de SendChannel
         * @see kotlinx.coroutines.channels.Channel é uma interface SendChannel
         */
        override fun onCompleted() = channel.close()
    }

    api.register(callback)
    awaitClose {
        api.unregister(callback)
    }
}


private suspend fun checkFromFlow() {

    var cb: Callback<Int>? = null
    val api = object: CallbaclBasedApi<Int> {
        override fun register(callback: Callback<Int>) {
            cb = callback
        }

        override fun unregister(callback: Callback<Int>) {
            cb = null
        }
    }

    val consumer = flowfrom(api)

    (1 .. 3).forEach {
        cb?.onNextValue(it)
    }

    consumer.collect {
        println("Collected $it")
    }
}


fun main() {
    runBlocking {
        checkFromFlow()
    }
}