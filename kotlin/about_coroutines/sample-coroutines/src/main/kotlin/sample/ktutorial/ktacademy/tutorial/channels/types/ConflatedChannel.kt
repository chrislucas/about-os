package sample.ktutorial.ktacademy.tutorial.channels.types

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * https://www.baeldung.com/kotlin/channels#4-conflated-channel
 *
 * Conflated channel - Canal combinado
 *
 * Num canal combinado (conflated channel), o valor mais recente escrito
 * sobreescrevo o valor anterior. Assim, o metodo send da classe Channel
 * nunca suspende a coroutine que executa o metodo receive.
 *
 * - Vemos nos exemplos abaixo que a coroutine1 envia mais mensagens
 * ao canal do que a coroutine2 que recebe e no exemplo sem os metodos
 * de delay primeiro eh enviada todas as mensagens e a segunda coroutine2 só recebe a ultima
 *
 * - Esse comportamento é o esperado uma vez que o conflated channel "o valor mais recentemente
 * escrito sobreescreve o valor anterior"
 *      - Se ocorrer um delay maior ao enviar do que ao receber o comportamento sera
 *      de esperar o envio para depois receber, obviamente
 *      - Se ocorrer um delay maior ao receber em relacao ao envio, a coroutine que envia
 *      continua enviando ate a segunda coroutine ser capaz de receber o valor
 */


private fun checkConflatedChannel() {
    val channel = Channel<Int>(CONFLATED)
    runBlocking {
        launch {
            repeat(300) {
                println("Coroutine 1 - Sending $it")
                channel.send(it)
            }
        }

        launch {
            repeat(300) {
                println("Coroutine 1 - Receiving ${channel.receive()}")
            }
        }
    }
}


private fun checkConflatedChannelWithProducerDelay() {
    val channel = Channel<Int>(CONFLATED)
    runBlocking {
        launch {
            repeat(100) {
                delay(1000L)
                println("Coroutine 1 - Sending $it")
                channel.send(it)
            }
        }

        launch {
            repeat(100) {
                println("Coroutine 1 - Receiving ${channel.receive()}")
            }
        }
    }
}

private fun checkConflatedChannelWithReceiverDelay() {
    val channel = Channel<Int>(CONFLATED)
    runBlocking {
        launch {
            repeat(100) {
                delay(500L)
                println("Coroutine 1 - Sending $it")
                channel.send(it)
            }
        }

        launch {
            repeat(100) {
                println("Coroutine 2 - Receiving ${channel.receive()}")
                delay(1500L)

            }
        }
    }
}

fun main() {
    checkConflatedChannelWithReceiverDelay()
}