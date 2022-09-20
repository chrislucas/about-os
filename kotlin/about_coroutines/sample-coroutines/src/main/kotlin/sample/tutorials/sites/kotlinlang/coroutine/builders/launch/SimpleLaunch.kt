package sample.tutorials.sites.kotlinlang.coroutine.builders.launch

import kotlinx.coroutines.*
import sample.ktutorial.logCoroutineScope
import kotlin.coroutines.CoroutineContext

/*
    https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/launch.html\
 */


private fun testLaunchInsideRunblocking() {
    runBlocking {

        fun launchNonblockingCoroutine(ctx: CoroutineContext) {
            CoroutineScope(ctx).launch {
                logCoroutineScope("Scope: $this.\nCurrent CoroutineScope: ${currentCoroutineContext()}\n.Ola sou um launcher")
            }
        }

        launchNonblockingCoroutine(Dispatchers.Unconfined)
        println("**************************************************************************")
        launchNonblockingCoroutine(Dispatchers.IO)
        println("**************************************************************************")
        launchNonblockingCoroutine(Dispatchers.Default)

    }
}


suspend fun testLaunch() {
    /*
        launch
        https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/launch.html

        Launches a new coroutine WIHTOUT blocking the current thread and returns a reference to the coroutine
        as a JOB (https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/)


     */
    CoroutineScope(Dispatchers.IO).launch {
        logCoroutineScope("Hello")
    }
}


fun checkMainContext() {
    /*
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                logCoroutineScope("Hello")
            }
        }
     */

    CoroutineScope(Dispatchers.IO).launch {
        withContext(this.coroutineContext) {
            logCoroutineScope("${0xff}")
        }
    }
}



fun main() {

}
