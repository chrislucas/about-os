package sample.functional.sam.intterface

import java.util.concurrent.BlockingQueue
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * https://kotlinlang.org/docs/java-interop.html#sam-conversions
 * Kotlin suporta SAM convensions tanto nas SAM interfaces em Java e Kotlin
 *
 */

private fun checkSAMConventions() {
    val runnable = Runnable { println("Runnable") }

    runnable.run()

    val queue = ScheduledThreadPoolExecutor(2)
    val executor = ThreadPoolExecutor(
        2, 4, 4000L,
        TimeUnit.MILLISECONDS, queue.queue
    )

    executor.execute {
        println("ThreadPoolExecutor")
    }

    executor.execute(Runnable {
        println("ThreadPoolExecutor 2")
    })

    executor.shutdown()
}


fun main() {
    checkSAMConventions()
}