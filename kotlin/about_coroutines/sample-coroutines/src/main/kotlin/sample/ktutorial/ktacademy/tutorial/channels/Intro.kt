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

// TODO estudar porque a funcao nao termina sua execucao
@OptIn(ExperimentalCoroutinesApi::class)
private fun simpleCommunication() {
    val channel = Channel<String>()

    runBlocking {
        launch {
            //logCoroutineScope("Coroutine Producer 1 $this")
            // coroutine 1
            println("Sending 1")
            channel.send("Hello World 1!!!")
            println("Sending 2")
            channel.send("Hello World 2!!!")
        }

        val consumer1 = async {
            if (channel.isEmpty) {
                "Consumer 1 _ Channel is Empty"
            }
            else {
                //logCoroutineScope("Coroutine Consumer 1 $this")
                // coroutine 2
                channel.receive()
            }
        }

        val consumer2 = async {
            if (channel.isEmpty)
                "Consumer 2 _ Channel is Empty"
            else {
                //logCoroutineScope("Coroutine Consumer 2 $this")
                // coroutine 2
                channel.receive()
            }
        }

        val consumer3 = async {
             if (channel.isEmpty) {
                "Consumer 3 _ Channel is Empty"
            }
            else {
                // logCoroutineScope("Coroutine Consumer 3 $this")
                // coroutine 2
                channel.receive()
            }
        }

        launch {
            if (channel.isEmpty) {
                println("Consumer 4 _ Channel is Empty")
            } else {
                logCoroutineScope("Coroutine Consumer 4 $this\nReceive Message: ${channel.receive()}")
            }
        }

        logCoroutineScope("Consumer 1 Received Message: ${consumer1.await()}")
        logCoroutineScope("Consumer 2 Received Message: ${consumer2.await()}")
        logCoroutineScope("Consumer 3 Received Message: ${consumer3.await()}")

        if (channel.isEmpty)
            return@runBlocking
    }

    channel.close()
}

fun main() {
    simpleCommunication()
}