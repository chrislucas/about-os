package sample.ktutorial.docs.basics.scope.concurrency.launch

import kotlinx.coroutines.*
import sample.ktutorial.docs.basics.scope.concurrency.dtf
import java.time.LocalDateTime

/*
    Objetivo:
    Criar uma funcao capaz de de construir e retornar um escopo de Coroutine que
    execute uma funcao lambda. Para cada funcao lambad adicione um delay diferente
    , uma TAG para identificar a funcao e imprima um TIMER para que possamos identificar
    a que horas a funcao foi executada
 */

suspend fun create(fn: suspend () -> Unit): Job {
    return coroutineScope {
        launch {
            fn()
        }
    }
}

/*
    https://mkyong.com/java/java-how-to-get-current-date-time-date-and-calender/
 */

fun createTimer(delayMillis: Long, tag: String): suspend () -> Job {
    return suspend {
        create {
            while (true) {
                println("Tag: $tag - ${dtf.format(LocalDateTime.now())}")
                delay(delayMillis)
            }
        }
    }
}


fun firstAttempt() {

    val timers = arrayOf(
        createTimer(1000L, "A"),
        createTimer(1500L, "B"),
        createTimer(2500L, "C"),
        createTimer(3000L, "C"),
    )

    val jobs = Array<Job?>(timers.size) { null }

    CoroutineScope(Dispatchers.IO).launch {
        timers.forEachIndexed { i, fn ->
            jobs[i] = fn.invoke()
        }
    }

    runBlocking {
        var acc = 0
        do {
            delay(1000L)
            acc += 1
        } while (acc < 10)

        jobs.forEach {
            it?.cancel()
        }
    }
}


fun main() {
    firstAttempt()
}