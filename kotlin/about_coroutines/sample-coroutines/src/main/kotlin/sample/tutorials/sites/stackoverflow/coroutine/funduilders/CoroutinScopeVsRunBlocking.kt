package sample.tutorials.sites.stackoverflow.coroutine.funduilders

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 *  https://stackoverflow.com/questions/74184077/kotlin-coroutines-runblocking-vs-coroutinescope
 */


suspend fun checkCoroutineBuilder() {
    var acc = 0
    coroutineScope {
        for (i in 0 .. 1000) {
            launch {
                delay(1000)
                acc += 1
            }
        }
    }
    println("checkCoroutineBuilder() = $acc")
}

fun checkRunblockingBuilder() = runBlocking {
    var acc = 0
    repeat(1000) {
        launch {
            delay(10)
            acc += 1
        }
    }
    println("checkRunblockingBuilder() = $acc")
}


fun main() {
    runBlocking {
        checkCoroutineBuilder()
    }

    checkRunblockingBuilder()

}