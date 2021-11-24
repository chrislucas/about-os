package sample.ktutorial.docs.channel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


/*
    https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/channel-flow.html
 */


@OptIn(ExperimentalCoroutinesApi::class)
private fun <T> Flow<T>.merger(concurrent: Flow<T>) = channelFlow {

    // Coleta de uma coroutine e envia a coleta
    launch {
        collect {
            send(it)
        }
    }
    // coleta de uma coroutine e envia para uma concorrente

    concurrent.collect {
        send(it)
    }
}


@OptIn(ExperimentalCoroutinesApi::class)
fun <T, R> contextualFlow(fn: () -> R) = channelFlow {
    launch (Dispatchers.IO) {
        send(fn())
    }

    launch (Dispatchers.Default) {
        send(fn())
    }
}