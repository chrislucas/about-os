package sample.tutorials.sites.medium.coroutines

import sample.ktutorial.logCoroutineScope
import sample.ktutorial.logThreadScope
import kotlin.concurrent.thread
import kotlin.math.log


/**
 *
 * https://reflectoring.io/understanding-kotlin-coroutines-tutorial/
 *
 * Pontos interessantes
 *  - "Coroutine are computations that run on top of thread"
 *  - "We can suspend a coroutine to allow other to run on the same thread,
 *  we can further resume the coroutine to run on the same or a different thread"
 *
 *  -"When a coroutine is suspended, it computation is paused, removed from the thread
 *  and stored in memory leaving the thread free to execute other activities."
 *      - "This way we can run many coroutines concurrently using only a small pool of threads"
 */


private fun checkThreadPerformance() {
    fun fakeLongRunningFun() {
        logThreadScope("Before start Fake Long Operation ...")
        Thread.sleep(1000)
        logThreadScope("After end Fake Long Operation ...")
    }

    fun checkA() {
        /*
            Note que a funcao checkA eh executada enuma thread diferente
            da execucao da funcao fakeLongRunningFun()
         */
        logThreadScope("Before call FakeLongRunningFunction ...")
        thread {
            fakeLongRunningFun()
        }
        logThreadScope("After call FakeLongRunningFunction ...")
    }

    fun checkB() {
        /*
            Aqui todas as chamadas sao executadas na mesma thread
         */
        thread {
            logThreadScope("Before call FakeLongRunningFunction ...")
            fakeLongRunningFun()
            logThreadScope("After call FakeLongRunningFunction ...")
        }
    }
    checkA()
}

fun main() {
    checkThreadPerformance()
}