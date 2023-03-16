package sample.ktutorial

import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.job
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext


suspend fun logCoroutineScope(message: String): Thread =
    Thread.currentThread().apply {
        val aboutJob = coroutineContext.job.about()
        println(
            String.format(
                "Thread Name: [%s]\nThreadRef: [%s]\nThread ContextClassLoader[%s]\n" +
                        "CurrentCoroutineContext: %s\nJob: %s\nMessage: [%s]\n",
                name,
                this,
                contextClassLoader,
                currentCoroutineContext(),
                aboutJob,
                message
            )
        )
    }

fun Job.about() =
    String.format(
        "Job[%s]:Element[%s], [Is active? %s, Is cancelled? %s, Is completed? %s]",
        job,
        get(key),
        isActive,
        isCancelled,
        isCompleted,
    )


fun Job.childrenToString() = if (children.none()) {
    "empty list of child"
} else {
    children.joinToString(", ")
}

fun logThreadScope(message: String): Thread =
    Thread.currentThread().apply {
        println(String.format("Thread Name: [%s]\nMessage: [%s]\n", name, message))
    }
