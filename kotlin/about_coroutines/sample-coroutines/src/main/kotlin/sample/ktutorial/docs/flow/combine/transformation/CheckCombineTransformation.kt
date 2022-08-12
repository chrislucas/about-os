package sample.ktutorial.docs.flow.combine.transformation

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking

/*
    https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/combine-transform.html
 */

private val alpha = 'a'..'z'

private val stringFlow = flow<String> {
    for (i in alpha) {
        emit("$i")
    }
}


private val intFlow = flow<Int> {
    for (i in alpha.withIndex()) {
        emit(i.index)
    }
}


private suspend fun checkCombineFlow() {
    stringFlow.combineTransform(intFlow) { p, q ->
        emit(Pair(p, q))
    }.take(10).collect(::println)
}


fun main() {
    runBlocking {
        checkCombineFlow()
    }
}