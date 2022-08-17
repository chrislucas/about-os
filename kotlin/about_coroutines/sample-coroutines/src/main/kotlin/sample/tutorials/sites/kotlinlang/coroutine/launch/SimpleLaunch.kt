package sample.tutorials.sites.kotlinlang.coroutine.launch

import kotlinx.coroutines.*
import sample.ktutorial.logCoroutineScope
import kotlin.coroutines.CoroutineContext

/*
    https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/launch.html
 */
fun main() {
    runBlocking {

        fun launchNonblockingCoroutine(ctx: CoroutineContext) {
            CoroutineScope(ctx).launch {
                logCoroutineScope("Scope: $this.\nCurrent CoroutineScope: ${currentCoroutineContext()}\n.Ola sou um launcher")
            }
        }

        launchNonblockingCoroutine(Dispatchers.Unconfined)
        println("**************************************************************************")
        launchNonblockingCoroutine(Dispatchers.IO)
    }
}
