package sample.ktutorial.docs.channels

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

/*
       https://play.kotlinlang.org/hands-on/Introduction%20to%20Coroutines%20and%20Channels/01_Introduction
       "Deffered values prove uma maneira de transferir um dado unico entre coroutines"
       "Channels prove uma forma de transferir stream de valores entre coroutines"
 */

val channel = Channel<Int>()

private suspend fun producer() {
    CoroutineScope(Dispatchers.IO).launch {
        delay(10000L)
        for (i in 1..100) {
            //delay(1000L)
            channel.send(i * i)
        }
    }
}

private fun consumer(quantity: Int) = runBlocking {
    repeat(quantity) {
        println(channel.receive())
    }

    println("Finish")
}


fun main() {
    CoroutineScope(Dispatchers.Unconfined).launch {
        producer()
    }

    consumer(14)
}