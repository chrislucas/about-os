package sample.ktutorial.docs.concurrency

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import sample.ktutorial.docs.logCoroutineScope

/**
 * https://gist.github.com/fikr4n/d0edd0d5e76b22fa8fa2fab77d8f05b1
 */
class FixedPool(private val workers: Int) {
    private val channelTasks = Channel<Task>()
    private val jobs = mutableListOf<Job>()

    init {
        start()
    }

    private fun start() {
        repeat(workers) {
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

    fun execute(task: Task) {
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

data class Task(private val name: String, private val time: Long) {
    operator fun invoke() {
        Thread.sleep(time)
    }
}


private fun checkPool() {
    val pool = FixedPool(2)
    runBlocking {
        pool.execute(Task("A", 1000))
        pool.execute(Task("B", 2500))
        pool.execute(Task("C", 4000))
        pool.execute(Task("E", 500))
        pool.close()
        pool.join()
    }
}

fun main() {
    checkPool()
}