package sample.ktutorial.docs.suspendcoroutines

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.contracts.contract
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun <T> checkSuspendCoroutine(): T? = suspendCoroutine { continuation ->
    println(continuation)
}

suspend fun <T> checkSuspendCancellableCoroutine(): T? = suspendCancellableCoroutine { cancellableContinuation ->
    println(cancellableContinuation)
}

suspend fun checkSupervisorScope(): Unit = supervisorScope {
    println(this)
}


suspend fun <T> checkSuspendCoroutineUnInterceptedOrReturn(fn: () -> T?): T? =
    suspendCoroutineUninterceptedOrReturn { continuation ->
        println(continuation)
    }


fun main() {
    runBlocking {
        checkSupervisorScope()
        checkSuspendCoroutineUnInterceptedOrReturn {}
    }
}
