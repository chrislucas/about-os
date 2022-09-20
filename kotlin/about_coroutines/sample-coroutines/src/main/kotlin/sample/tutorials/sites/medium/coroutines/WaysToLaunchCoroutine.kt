package sample.tutorials.sites.medium.coroutines

import kotlinx.coroutines.*
import sample.ktutorial.logCoroutineScope
import kotlin.coroutines.CoroutineContext

val suspendableA: (CoroutineContext, suspend () -> Unit) -> Unit = { ctx, fn ->
    CoroutineScope(ctx).launch {
        fn()
    }
}

val notSuspendableA: (CoroutineContext, () -> Unit) -> Unit = { ctx, fn ->
    CoroutineScope(ctx).launch {
        fn()
    }
}

val suspendableB: (suspend () -> Unit) -> Unit = { fn ->
    CoroutineScope(CoroutineName("Launching a coroutine")).launch {
        fn()
    }
}

val notSuspendableB: (() -> Unit) -> Unit = { fn ->
    CoroutineScope(CoroutineName("Launching a coroutine")).launch {
        fn()
    }
}


fun main() {
    runBlocking {
        suspendableB {
            logCoroutineScope("Exec b")
        }
    }

    notSuspendableB {

    }
}