package sample.tutorials.sites.kotlinlang.flow.channelflow

import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking

/*
    https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/channel-flow.html
 */


val stringFlow = channelFlow<String> {
    for ((i, v) in ('a'..'z').withIndex()) {
        send("$i, $v")
    }
}


fun main() {
    runBlocking {
        stringFlow
            .collect(::println)

        stringFlow
            .map { it }             // operador intermediario
            .take(10)        // operador intermediario
            .collect(::println)     // operador terminal que dispara os intermediarios
    }
}