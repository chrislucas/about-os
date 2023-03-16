@file:OptIn(DelicateCoroutinesApi::class)

package sample.ktutorial.docs.sharedmutablestate

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import sample.tutorials.sites.medium.coroutines.fakeTask

/**
 * https://kotlinlang.org/docs/shared-mutable-state-and-concurrency.html#thread-confinement-fine-grained
 *
 * Prmeira parte do tutorial
 * @see sample.ktutorial.docs.sharedmutablestate.fakeMassiveAction
 *
 */


@DelicateCoroutinesApi
@OptIn(DelicateCoroutinesApi::class)
val counterContext = newSingleThreadContext(name = "CounterContext")

private fun check() = runBlocking {
    with(Dispatchers.Default) {
        fakeMassiveAction {

        }
    }
}


fun main() {

}