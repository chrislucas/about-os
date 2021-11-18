package sample.ktutorial.docs.flow.builders.aasflow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import sample.ktutorial.docs.flow.builders.flowof.VALUES
import sample.ktutorial.docs.flow.builders.flowof.isEven

/*
    https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/as-flow.html

    Create a cold flow that produces a single value from a given functional type
 */


private fun check() = runBlocking(Dispatchers.IO) {
    val job = VALUES.asFlow()
        .filter { isEven(it) }
        .onEach { println(it) }
        .launchIn(this)

    println(this)
    println(job)
}

private fun checkAsFlowOp(scope: CoroutineScope) {
    val job = VALUES.asFlow()
        .filter { isEven(it) }
        .onEach { println(it) }
        .launchIn(scope)

    println(job)
    runBlocking {
        job.cancelAndJoin()
        job.invokeOnCompletion {
            println("Complete")
        }
    }
}

fun main() {
    //checkAsFlowOp(CoroutineScope(Dispatchers.Default))
    checkAsFlowOp(CoroutineScope(Dispatchers.IO))
}