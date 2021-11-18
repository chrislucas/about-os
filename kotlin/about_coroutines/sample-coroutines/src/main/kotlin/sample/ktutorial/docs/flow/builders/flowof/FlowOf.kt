package sample.ktutorial.docs.flow.builders.flowof


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/*
    functions to create a flow from a fixed set of values.
 */


fun isEven(i: Int) = i and 1 == 0

fun isOdd(i: Int) = i and 1 == 1


val VALUES = (1..1000)

val CONSTANT = flowOf(VALUES.filter(::isEven), VALUES.filter(::isOdd))


private fun check() {
    CONSTANT.onEach { println(it) }
        .launchIn(CoroutineScope(Dispatchers.Unconfined))
}


fun main() {
    check()
}