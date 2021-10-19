package sample.ktutorial.composing.lazily

/*

 Documentacao

 Calls the specified suspending block with a given coroutine context, suspends until it completes, and returns
 the result.

 The resulting context for the [block] is derived by merging the current [coroutineContext] with the
 specified [context] using `coroutineContext + context` (see [CoroutineContext.plus]).
 This suspending function is cancellable. It immediately checks for cancellation of
 the resulting context and throws [CancellationException] if it is not [active][CoroutineContext.isActive].

 This function uses dispatcher from the new context, shifting execution of the [block] into the
 different thread if a new dispatcher is specified, and back to the original dispatcher
 when it completes. Note that the result of `withContext` invocation is
 dispatched into the original context in a cancellable way with a*prompt cancellation guarantee**,
 which means that if the original [coroutineContext], in which `withContext` was invoked,
 is cancelled by the time its dispatcher starts to execute the code,
 it discards the result of `withContext` and throws [CancellationException].

 The cancellation behaviour described above is enabled if and only if the dispatcher is being changed.
 For example, when using `withContext(NonCancellable) { ... }` there is no change in dispatcher and
 this call will not be cancelled neither on entry to the block inside `withContext` nor on exit from it.

 Assinatura:

    public suspend fun <T> withContext(
        context: CoroutineContext,
        block: suspend CoroutineScope.() -> T
    ): T


 */


fun main() {

}