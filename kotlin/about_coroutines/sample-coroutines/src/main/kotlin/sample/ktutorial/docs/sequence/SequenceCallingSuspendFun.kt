package sample.ktutorial.docs.sequence

import kotlinx.coroutines.*
import sample.ktutorial.docs.logCoroutineScope

private suspend fun <R> fakeOperation(fn: () -> R) =
    fn()

private fun simpleOperation() = sequence {
    logCoroutineScope("SimpleOperation")

    val scope = CoroutineScope(Dispatchers.IO)

    val rs = scope.async {
        fakeOperation {
            (1 .. 1000).toList()
        }
    }
    yield(rs)
}


fun main() {
    simpleOperation().forEach {
        runBlocking {
            println(it.await())
        }
    }
}