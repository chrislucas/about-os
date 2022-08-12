package sample.ktutorial.docs.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.channelFlow
import sample.ktutorial.logCoroutineScope


/**
 * https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/channel-flow.html
 *
 * creates an instance of a cold flow with elements that are sent to a
 * @see kotlinx.coroutines.channels.SendChannel
 */

private suspend fun checkChannelFlowMultiplesCoroutines() {
    val IntFlow = channelFlow {

        launch(Dispatchers.Unconfined) {
            logCoroutineScope("Emit inside launch(Dispatchers.Unconfine) - $this")
            send(3)
        }

        launch {
            logCoroutineScope("Emit inside launch - $this")
            send(4)
        }
        // Nao vai emiter por se tratar de outra coroutine
        CoroutineScope(Dispatchers.Default).launch {
            logCoroutineScope("Emit inside CoroutineScope(Dispatchers.Default) - $this")
            send(5)
        }

        // Nao vai emiter por se tratar de outra coroutine
        CoroutineScope(Dispatchers.IO).launch {
            logCoroutineScope("Emit inside CoroutineScope(Dispatchers.IO) - $this")
            send(6)
        }

        GlobalScope.launch {
            logCoroutineScope("Emit inside GlobalScope.launch")
            send(7)

            launch(Dispatchers.IO){
                logCoroutineScope("Emit inside launch(IO) $this")
                send(8)
            }

            withContext(CoroutineName("InsideGlobalScope")) {
                logCoroutineScope("Emit inside withContext CoroutineName")
                send(9)
            }

            withContext(Dispatchers.IO) {
                logCoroutineScope("Emit inside withContext Dispatchers.Unconfined")
                send(10)
            }
        }
    }

    IntFlow.collect {
        println("Collect $it")
    }
}

private suspend fun checkSendValueInsideRunBlocking() {
    val IntFlow = channelFlow {
        runBlocking {
            send(1)
            send(2)
            send(3)
        }
    }

    IntFlow.collect {
        println("Collect $it")
    }

}


fun main() {
    runBlocking {
        //checkChannelFlowMultiplesCoroutines()
        checkSendValueInsideRunBlocking()
    }
}