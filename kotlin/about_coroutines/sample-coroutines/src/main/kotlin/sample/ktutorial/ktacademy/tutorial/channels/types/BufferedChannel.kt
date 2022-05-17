package sample.ktutorial.ktacademy.tutorial.channels.types

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import sample.ktutorial.logCoroutineScope

/**
 * https://www.baeldung.com/kotlin/channels#2-buffered-channel
 *
 * Podemos predefinir o tamanho do buffer do canal atraves de seu contrutor
 */


@OptIn(ExperimentalCoroutinesApi::class)
private fun checkBufferedChannel() {
    val channel = Channel<String> (1) {
        println("onUndeliveredElement method $it")
    }

    runBlocking {
        launch {
            logCoroutineScope("Producer")
            for (c in 'a' .. 'c') {
                println("Sending $c")
                channel.send("$c")
            }
        }

        launch {
            logCoroutineScope("Receiver")
            repeat(3) {
                delay(1000)
                println("Receiving ${channel.receive()}, IsEmpty: ${channel.isEmpty}")
                if (channel.isEmpty) {
                    return@launch
                }
            }
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
private fun checkufferedChannelWithAsync() {

    val channel = Channel<String> (1) {
        println("onUndeliveredElement method $it")
    }

    runBlocking {
        launch {
            for (c in 'a' .. 'k') {
                println("Sending $c")
                channel.send("$c")
            }
        }

        val receiver1 = async {
            /*
            if (channel.isEmpty) {
                println("Receiver 1 _ Channel Is Empty")
                return@async
            }

             */
            repeat(3) {
                println("Receiver 1: ${channel.receive()}")
            }
        }
        receiver1.await()

        val receiver2 = async {
            /*
            if (channel.isEmpty) {
                println("Receiver 2 _ Channel Is Empty")
                return@async
            }

             */
            repeat(3) {
                println("Receiver 2: ${channel.receive()}")
            }
        }
        receiver2.await()

        if (channel.isEmpty) {
            return@runBlocking
        }
        else {
            while (!channel.isEmpty) {
                println("Receiving: ${channel.receive()}")
            }
        }
    }
}


/**
 * Compare o resultado obitido com um Canal com um buffer e sem ele
 * @see rendezvousChannelWith1Receiver
 */
private fun checkBufferedChannelWoth1Receiver() {
    val channel = Channel<String> (1) {
        println("onUndeliveredElement method $it")
    }
    runBlocking {
        launch {
            logCoroutineScope("Producer")
            for (c in 'a' .. 'c') {
                println("Sending $c")
                channel.send("$c")
            }
        }
        launch {
            logCoroutineScope("Receiver")
            repeat(3) {
                delay(1000)
                println("Receiving ${channel.receive()}")
            }
        }
    }
}

/**
 * @see unlimitedChannelWith1Receiver
 * @see send100MessagesWithRendezvousChannel
 */

private fun send100MessagesWithBufferedChannelWoth1Receiver() {
    val buffer = 10
    val channel = Channel<Int> (buffer) {
        println("onUndeliveredElement method $it")
    }

    val times = 100
    runBlocking {
        launch {
            repeat(times) {
                //delay(1000)
                println("Coroutine 1 Sending: $it")
                channel.send(it)
            }
        }
        launch {
            repeat(times) {
                delay(500)
                println("Coroutine 2 Receiving: ${channel.receive()}")
            }
        }
    }
}


private fun send100MessagesWithBufferedChannelWoth1ReceiverAndDelay() {
    val buffer = 10
    val channel = Channel<Int> (buffer) {
        println("onUndeliveredElement method $it")
    }

    val times = 100
    runBlocking {
        launch {
            repeat(times) {
                delay(500)
                println("Coroutine 1 Sending: $it")
                channel.send(it)
            }
        }
        launch {
            repeat(times) {
                delay(1200)
                println("Coroutine 2 Receiving: ${channel.receive()}")
            }
        }
    }
}

fun main() {
    send100MessagesWithBufferedChannelWoth1ReceiverAndDelay()
}