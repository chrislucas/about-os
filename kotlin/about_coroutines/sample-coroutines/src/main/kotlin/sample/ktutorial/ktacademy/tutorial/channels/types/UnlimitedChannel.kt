package sample.ktutorial.ktacademy.tutorial.channels.types

import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import sample.ktutorial.logCoroutineScope

/***
 * https://www.baeldung.com/kotlin/channels#3-unlimited-channel
 * - Um canal ilimitado possui buffer de capacidade imilitaad
 * - Mas deve ser usado com cautela pois podemos cair num OutOfMemoryError
 * se o buffer sofrer um overload, caso nao tenha memoria mais disponivel
 */

/**
 * veja os resultados obitidos dos metodos abaixo
 * @see rendezvousChannelWith1Receiver
 * @see checkBufferedChannelWoth1Receiver
 *
 * O resultado abaixo é, a coroutine1 vai enviar 100 mensagems
 * para o canal sem ser bloqueada pela coroutine2, ja que
 *  buffer é de INT_MAX
 *
 * @see send100MessagesWithBufferedChannelWoth1Receiver
 */
fun unlimitedChannelWith1Receiver() {
    val channel = Channel<Int>(UNLIMITED)
    runBlocking {
        launch {
            logCoroutineScope("Coroutine 1 | $this")
            repeat(100) {
                println("Coroutine 1 Sending $it")
                channel.send(it)
            }
        }

        launch {
            logCoroutineScope("Coroutine 2 | $this")
            repeat(100) {
                delay(500)
                println("Coroutine 2 Receiving: ${channel.receive()}")
            }
        }
    }
}

fun unlimitedChannelWith2Receiver() {
    val channel = Channel<Int>(UNLIMITED)

    runBlocking {
        launch {
            logCoroutineScope("Coroutine 1 | $this")
            repeat(15) {
                println("Coroutine 1 Sending $it")
                //delay(1000)
                channel.send(it)
            }
        }

        launch {
            logCoroutineScope("Coroutine 2 | $this")
            repeat(5) {
                delay(2000)
                println("Coroutine 2 Receiving: ${channel.receive()}")
                //if (channel.isEmpty)
                    //return@launch
            }
        }

        launch {
            logCoroutineScope("Coroutine 3 | $this")
            repeat(5) {
                delay(1500)
                println("Coroutine 3 Receiving: ${channel.receive()}")
                //if (channel.isEmpty)
                    //return@launch
            }
        }
    }
}


fun asyncUnlimitedChannelWith2Receive() {
    // INT_MAX hehehe
    val channel = Channel<Int>(UNLIMITED)

    runBlocking {
        val s = async {
            logCoroutineScope("Coroutine 1 | $this")
            repeat(15) {
                println("Coroutine 1 Sending $it")
                channel.send(it)
            }
        }

        launch {
            logCoroutineScope("Coroutine 2 | $this")
            s.await()
            repeat(5) {
                delay(500)
                println("Coroutine 2 Receiving: ${channel.receive()}")
            }
        }

        launch {
            logCoroutineScope("Coroutine 3 | $this")
            s.await()
            repeat(5) {
                delay(200)
                println("Coroutine 3 Receiving: ${channel.receive()}")
            }
        }
    }
}

fun main() {
    unlimitedChannelWith1Receiver()
}