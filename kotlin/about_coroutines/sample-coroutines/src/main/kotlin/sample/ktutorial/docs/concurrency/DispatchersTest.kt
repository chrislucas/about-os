package sample.ktutorial.docs.concurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import sample.ktutorial.logCoroutineScope
import kotlin.math.log


fun dispatcherDefault() {
    CoroutineScope(Dispatchers.Default).launch {
        logCoroutineScope("Hello World Dispatcher Default")
    }
}

fun dispatcherUnconfined() {
    CoroutineScope(Dispatchers.Unconfined).launch {
        logCoroutineScope("Hello World Dispatcher Unconfined")
    }
}

fun dispatcherIO() {
    CoroutineScope(Dispatchers.IO).launch {
        logCoroutineScope("Hello World Dispatcher IO")
    }
}

fun main() {

    runBlocking {
        dispatcherIO()
    }

    runBlocking {
        dispatcherDefault()
    }

    runBlocking {
        dispatcherUnconfined()
    }
}