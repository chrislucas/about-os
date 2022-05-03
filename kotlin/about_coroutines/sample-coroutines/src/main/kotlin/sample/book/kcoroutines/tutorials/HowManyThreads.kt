package sample.book.kcoroutines.tutorials

import kotlinx.coroutines.*
import sample.ktutorial.docs.logCoroutineScope
import kotlin.coroutines.suspendCoroutine

/**
 * https://stackoverflow.com/questions/67795266/how-many-threads-are-created-by-multiple-coroutines-in-kotlin
 */

fun externalLog() {
    println("***** Start ExternalLog *****")
    for (i in 0..5) {
        logCoroutineScope("Message: $i")
    }
    println("***** End ExternalLog *****")
}

@OptIn(DelicateCoroutinesApi::class)
private fun checkGlobalScope() {

    fun internalLog() {
        println("***** Start InternalLog *****")
        for (i in 0..5) {
            logCoroutineScope("Message: $i")
        }
        println("***** End InternalLog *****")
    }

    GlobalScope.launch(context = CoroutineName("C1")) {
        internalLog()
    }

    GlobalScope.launch(context = CoroutineName("C2")) {
        externalLog()
    }

    GlobalScope.launch(context = CoroutineName("C3")) {
        println("***** Start[$this] *****")
        for (i in 0..5) {
            logCoroutineScope("Message: $i")
        }
        println("***** End[$this] *****")
    }
}

private suspend fun checkLaunchDispatcherDefault() {
    fun internalLog() {
        println("***** Start InternalLog *****")
        for (i in 0..5) {
            logCoroutineScope("Message: $i")
        }
        println("***** End InternalLog *****")
    }

    CoroutineScope(Dispatchers.Default).launch(CoroutineName("C1#")) {
        internalLog()
    }

    CoroutineScope(Dispatchers.Default).launch(CoroutineName("C12")) {

        suspendCoroutine<Unit> {
            println("***** C2# Start[$this] *****")
            for (i in 0..10) {
                logCoroutineScope("Message: $i")
            }
            println("***** C2# End[$this] *****")
        }
    }

    CoroutineScope(Dispatchers.IO).launch(CoroutineName("C3#")) {
        println("***** C3# Start[$this] *****")
        for (i in 0..10) {
            logCoroutineScope("Message: $i")
        }
        println("***** C3# End[$this] *****")
    }

    runBlocking(CoroutineName("C4#")) {
        println("***** Runblocking Start[$this] *****")
        for (i in 0..30) {
            logCoroutineScope("Runblocking C4# Message: $i")
        }
        println("***** Runblocking End[$this] *****")
    }


    withContext(CoroutineScope(Dispatchers.Default).coroutineContext + CoroutineName("C5#")) {
        println("***** C5# WithContext Start[$this] *****")
        for (i in 0..10) {
            logCoroutineScope("C5# WithContext Message: $i")
        }
        println("***** C5# WithContext End[$this] *****")
    }

}

fun main() {
    runBlocking {
        //checkGlobalScope()
        checkLaunchDispatcherDefault()
    }
}