package sample.ktutorial.docs.ctxanddispatchers.jumpingthread

import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import sample.ktutorial.docs.logCoroutineScope

/*
    https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html#jumping-between-threads
 */
@OptIn(ObsoleteCoroutinesApi::class)
private fun check() = runBlocking {
    newSingleThreadContext("ctx1").use { ctx1 ->
        newSingleThreadContext("ctx2").use { ctx2 ->
            logCoroutineScope("Started in main")

            /*
                1) testando o runblocking com um CoroutineContext explicitamente definido
                2) Mudar o contexto da coroutine usando a funcao withContext
             */
            runBlocking(ctx1) {
                logCoroutineScope("Started In ctx1")
                withContext(ctx2) {
                    // mudando o contexo da coroutine
                    logCoroutineScope("Working in ctx2")
                }
                logCoroutineScope("back to ctx1")
            }



            logCoroutineScope("back to main")
        }
    }
}


fun main() {
    check()
}