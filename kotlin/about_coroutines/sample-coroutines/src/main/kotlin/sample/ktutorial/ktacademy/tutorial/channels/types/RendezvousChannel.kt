package sample.ktutorial.ktacademy.tutorial.channels.types

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import sample.ktutorial.logCoroutineScope

/**
 * https://www.baeldung.com/kotlin/channels#types-of-channels
 *
 * - Existem 4 tipos de Channels, e eles diferem no numeo de valores que podem segurar
 */


/**
 * 3.1. Rendezvous Channel - https://www.baeldung.com/kotlin/channels#1-rendezvous-channel
 *
 * Canal de encontro/reuniao
 * Esse tipo de canal nao possui buffer.
 *
 * A coroutine de envio é suspena ate que uma coroutine
 * consumidora invoque o metodo recebimento do canal.
 *
 * Da mesma forma, uma coroutine
 * consumidora eh suspensa ate que uma coroutine produtora invoque o metoo de envio
 *
 */


private fun rendezvousChannel() {
    val channel = Channel<String>()
    runBlocking {

        launch {
            logCoroutineScope("Sending $this")
            for (c in ('a'..'c')) {
                channel.send("$c")
            }
        }

        launch {
            logCoroutineScope("Receive 1: $this")
            repeat(3) {
                delay(100)
                println(channel.receive())
            }
        }

        // TODO verificar pq o programa nao encerra

        launch {
            logCoroutineScope("Receive 2: $this")
            channel.receiveAsFlow().collect {
                delay(200)
                println("Flow: $it")
            }
        }
    }
}

fun main() {
    rendezvousChannel()
}