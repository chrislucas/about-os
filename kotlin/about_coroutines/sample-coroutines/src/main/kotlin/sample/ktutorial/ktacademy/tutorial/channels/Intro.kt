package sample.ktutorial.ktacademy.tutorial.channels

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import sample.ktutorial.docs.logCoroutineScope

/**
 * https://kt.academy/article/cc-channel
 *
 * - Channels permitem qu coroutines se comuniquem umas com as outras
 * - Um channel é concentualmente similar a uma Queue. Uma ou mais coroutines que produzem/retornam
 * valores podem escrever em um channel e outras coroutines que chamamos de consumer podem ler desse mesmo
 * channel
 */


private fun simpleCommunication() {
    val channel = Channel<String>()

    runBlocking {
        launch {
            logCoroutineScope("Coroutine Producer 1 $this")
            // coroutine 1
            channel.send("Hello World!!!")
        }

        val consumer1 = async {
            logCoroutineScope("Coroutine Consumer 1 $this")
            // coroutine 2
            channel.receive()
        }

        val consumer2 = async {
            logCoroutineScope("Coroutine Consumer 2 $this")
            // coroutine 2
            channel.receive()
        }


        val consumer3 = async {
            logCoroutineScope("Coroutine Consumer 3 $this")
            // coroutine 2
            channel.receive()
        }

        launch {
            //logCoroutineScope("Coroutine Consumer 4 $this\nReceive Message: ${channel.receive()}")
        }

        logCoroutineScope(consumer1.await())
        logCoroutineScope(consumer2.await())
        //logCoroutineScope(consumer3.await())
    }

    //channel.close()
}

fun main() {
    simpleCommunication()
}