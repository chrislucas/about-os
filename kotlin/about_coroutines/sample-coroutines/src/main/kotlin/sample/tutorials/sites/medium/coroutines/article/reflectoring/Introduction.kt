package sample.tutorials.sites.medium.coroutines.article.reflectoring

import kotlinx.coroutines.*
import sample.ktutorial.docs.flow.ctxA
import sample.ktutorial.logCoroutineScope
import sample.ktutorial.logThreadScope
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext


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

private fun rewriteThreadPerformanceUsingCoroutinesPart2() {

    suspend fun fakeTask(tag: String) {
        logCoroutineScope("[$tag]: Before call FakeTask ...")
        delay(1000)
        logCoroutineScope("[$tag]: After call FakeTask ...")
    }

    fun launchFakeTask(tag: String, coroutineScope: CoroutineScope) {
        runBlocking {
            logCoroutineScope("[$tag]: LFT2 - Before launch fakeTask")
            coroutineScope.launch { fakeTask(tag) }
            logCoroutineScope("[$tag]: LFT2 - After launch fakeTask")
        }
    }

    launchFakeTask("Task 2 exec 1", CoroutineScope(CoroutineName("CTX LFT2")))
    println("\n****************************************************************\n")
    launchFakeTask("Task 2 exec 2", CoroutineScope(Dispatchers.Unconfined))
    println("\n****************************************************************\n")
    launchFakeTask("Task 2 exec 3", CoroutineScope(Dispatchers.IO))
    println("\n****************************************************************\n")
    launchFakeTask("Task 2 exec 4", CoroutineScope(Dispatchers.Default))
}

private fun rewriteThreadPerformanceUsingCoroutinesPart1() {

    suspend fun fakeTask(tag: String) {
        logCoroutineScope("[$tag]: Before call FakeTask ...")
        delay(1000)
        logCoroutineScope("[$tag]: After call FakeTask ...")
    }

    fun launchFakeTask(tag: String, coroutineContext: CoroutineContext? = null) {
        /*
            Inicia uma nova coroutine, bloqueando a Main Thread
         */
        runBlocking {
            logCoroutineScope("[$tag]: LFT1 - Before launch fakeTask")
            /**
             * Inicia uma nova coroutine
             */

            if (coroutineContext == null) {
                launch {
                    /*
                        FakeTask é uma suspend function. Ela suspend a coroutine sem
                        bloquear a thread q ela esta vinculada permitindo outas coroutines
                        rodarem nessa mesma thread
                     */
                    fakeTask(tag)
                }
            } else {
                launch(coroutineContext) {
                    fakeTask(tag)
                }
            }
            logCoroutineScope("[$tag]: LFT1 - After launch fakeTask")
        }
    }

    launchFakeTask("Task 1 exec 1")
    launchFakeTask("Task 1 exec 2", CoroutineName("CTX test"))
}

private fun rewriteThreadPerformanceUsingCoroutinesPart3() {
    suspend fun fakeTask(tag: String) {
        logCoroutineScope("[$tag]: Before call FakeTask ...")
        delay(1000)
        logCoroutineScope("[$tag]: After call FakeTask ...")
    }

    fun launchFakeTask1(tag: String) {
        runBlocking {
            logCoroutineScope("[$tag]: LFT1 - Before launch fakeTask")
            /**
             * Inicia uma nova coroutine
             */
            suspend { fakeTask(tag) }.invoke()
            logCoroutineScope("[$tag]: LFT1 - After launch fakeTask")
        }
    }
    launchFakeTask1("Task 1 exec 1")
}


fun main() {
    rewriteThreadPerformanceUsingCoroutinesPart1()
    //rewriteThreadPerformanceUsingCoroutinesPart3()
}