package sample.ktutorial.docs.basics.scope.concurrency.manycoroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import sample.helpers.calculate
import sample.ktutorial.docs.basics.scope.concurrency.dtf
import kotlin.concurrent.thread


/*
    https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/jvm/test/guide/example-basic-06.kt

    https://kotlinlang.org/docs/coroutines-basics.html#coroutines-are-light-weight

 */




private fun executeXCoroutines(times: Int) = runBlocking {
    repeat(times) {
        launch {
            delay(5000L)
            println("Coroutine: $it -> ${dtf.format(java.time.LocalDateTime.now())}")
        }
    }
}

fun executeXThreads(times: Int) = runBlocking {
    repeat(times) {
        thread {
            Thread.sleep(1000L)
            println("Thread: $it -> ${dtf.format(java.time.LocalDateTime.now())}")
        }
    }
}


fun main() {
    val s2 = calculate {
        executeXCoroutines(1000)
    }

    val s1 = calculate {
        executeXThreads(1000)
    }


    println("T1: $s1\nT2: $s2")
}