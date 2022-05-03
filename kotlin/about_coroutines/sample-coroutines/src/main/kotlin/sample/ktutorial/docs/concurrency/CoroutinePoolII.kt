package sample.ktutorial.docs.concurrency

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import sample.ktutorial.docs.logCoroutineScope

/**
 * https://gist.github.com/fikr4n/d0edd0d5e76b22fa8fa2fab77d8f05b1
 * Modificando o codigo do link a cima para estudar o comportamento
 */

data class SuspendableTask(private val name: String, private val time: Long) {
    suspend operator fun invoke() {
        delay(time)
    }
}


data class TaskPool(private val poolSize: Int) {
    private val channelTasks = Channel<SuspendableTask>()
    private val jobs = mutableListOf<Job>()

    init {
        start()
    }

    private fun start() {
        repeat(poolSize) {
            val job = CoroutineScope(Dispatchers.Unconfined).launch {
                for (task in channelTasks) {
                    logCoroutineScope("$task started")
                    task()
                    logCoroutineScope("$task finished")
                }
            }

            jobs += job
        }
    }

    fun execute(task: SuspendableTask) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            channelTasks.send(task)
        }
    }

    suspend fun join() {
        for (job in jobs) {
            job.join()
        }
    }

    fun close() = channelTasks.close()
}


private fun checkPool() {
    val pool = TaskPool(2)
    runBlocking {
        pool.execute(SuspendableTask("A", 1000))
        pool.execute(SuspendableTask("B", 2500))
        pool.execute(SuspendableTask("C", 4000))
        pool.execute(SuspendableTask("E", 500))
        pool.close()
        pool.join()
    }
}


fun main() {
    checkPool()
}