package sample.ktutorial.docs.flow

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import sample.ktutorial.logCoroutineScope

/**
 * https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/buffer.html]
 *
 * Permite definir uma capacidade especifica para emitir dados e executa
 * o coletor numa coroutine diferente
 *
 * Flow<T>.buffer(capacity: Int = BUFFERED, onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND):
 *
 * capacity - buffer between coroutines. Allowed values are the sae as n Channel(...) factory function
 *
 * BUFFERED - default
 * CONFLATED, RENDEZVOUS, UNLIMITED, or a non-negative value indicating an explicityy requested sze
 *
 * onBufferOverflow - configures an action on buffer overflow
 *      - optional, default SUSPEND
 *      - supported only when capacity > 0 or capacity = Channel.BUFFERED
 *      - implicitly creeates a channel with at least one buffered element
 */


private suspend fun withoutBuffer() {
    /*
     * Normalmente, o flow é sequencial, significando que o codigo de cada operador e executado na mesma
     * coroutine
     *
     * Veja o exemplo
     */

    val f = ('a'..'e').asFlow()
    f.onEach {
        logCoroutineScope("1$it")
    }.collect {
        logCoroutineScope("2$it")
    }
    /**
     * o codigo acima mostra [1a, 2a, 1b, 2b ... 1e, 2e]
     * O total da execucao do codigo e a soma do tempo que leva cada operacao intermediaria
     * do flow (no exemplo acima so temos a onEach)
     */
    f.onEach {
        println("1$it")
    }.buffer().collect {
        println("2$it")
    }
}

private suspend fun withBuffer() {
    val f = ('a'..'e').asFlow()
    /**
     * Esse codigo ira exibir primeiro a mensagem em onEach e depois em collect
     * e usando a funcao logCouroutineScope vamos ver que o contexto das coroutines
     * sao diferentes.
     *
     * Eh utilizado 2 coroutines para executao desse codigo. Uma coroutine que eh criada
     * pela operador terminal collect e a outra para executar o codigo antes da chamada
     * do buffer
     */
    f.onEach { logCoroutineScope("1$it") }
        .buffer()   // buffer entre os operadores
        .collect { logCoroutineScope("2$it") }
}


private suspend fun suspendCoroutineBehavior() {
    val f = ('a'..'z').asFlow()
    /**
     * Se o codigo antes o buffer for mais rapido do que o codigo apos
     * o buffer, entao o canal ficara cheio em algum ponto e e a coroutine
     * producer (onEach no caso) sera suspensa ate que a consumer consiga alcançar a producer
     */
    f.onEach {
        delay(200L)
        logCoroutineScope("1$it")
    }.onCompletion { logCoroutineScope("OnEach Completion $this") }
        .buffer()
        .onCompletion { logCoroutineScope("Collect Completion $this") }
        .collect {
            delay(1500L)
            logCoroutineScope("2$it")
        }

}

private suspend fun overrideSuspendCoroutineBehavior() {
    val f = (1 .. 500).asFlow()
    f.onEach {
        //delay(300)
        logCoroutineScope("[Emmiter]: $it")
    }.onCompletion { logCoroutineScope("OnEach Completion $this") }
        /**
         * Por padrao o emitter é suspenso quando o buffer sofre overflow (o consumer
         * demora mais para processar do que o producer para fornecer).
         * Essa estrategia pode ser sobreescrita com o parametro onBufferOverFlow,
         * tal que o producer nunca seja suspenso.
         *
         * Em caso de buffer overflow existem 2 metidas a se tomar, ou o valor mais antigo
         * e descartado com o argumento DROP_OLDEST e o ultimo valor emitido e adicionado no buffer
         * ou o valor mais recente é descartado com o parametro DROP_LATEST
         */
        //.buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
        //.buffer(onBufferOverflow = BufferOverflow.DROP_LATEST)
        //.buffer(onBufferOverflow = BufferOverflow.SUSPEND)
        //.buffer(capacity = Channel.UNLIMITED, onBufferOverflow = BufferOverflow.DROP_LATEST)
            /*
                Requests a conflated channel in the Channel(...) factory function.
                This is a shortcut to creating a channel with onBufferOverflow = DROP_OLDEST.
             */
        //.buffer(capacity = Channel.CONFLATED)
            /*
                Requests a rendezvous channel in the Channel(...) factory function —
                a channel that does not have a buffer.
             */
        .buffer(capacity = Channel.RENDEZVOUS)
        .onCompletion {
            logCoroutineScope("Collect Completion $this")
        }
        .collect {
            delay(100L)
            logCoroutineScope("[Collector]: $it")
        }
}

/**
 * Operator fusion
 *
 * Uma chamada ao buffer passando um argumento ao parametro onBufferOverflow que nao seja o
 * argumento default, sobreescreve todas as operacoes de buffer precedentes (parametro
 * default é BufferOverflow.SUSPEND), porque ela nao suspende o seu upstream e nenhum buffer
 * upstream seria usado.
 */

suspend fun main() {
    runBlocking {
        //withoutBuffer()
        //withBuffer()
    }
    //suspendCoroutineBehavior()
    overrideSuspendCoroutineBehavior()
}