package sample.ktutorial.docs.channel

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import sample.ktutorial.logCoroutineScope

/*
    https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/channel-flow.html
 */

@OptIn(ExperimentalCoroutinesApi::class)
private fun check() = channelFlow {
    launch (CoroutineName("A")) {
        logCoroutineScope("Coroutine A")
        send(42)
    }

    launch(CoroutineName("B")) {
        logCoroutineScope("Coroutine B")
        send(43)
    }
}


private fun checkChannelFlow() = runBlocking {
    check().collect {
        println(it)
    }
}


fun main() {
    checkChannelFlow()
}