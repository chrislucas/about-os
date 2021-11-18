package sample.ktutorial.docs.flow.builders.flow.operator.filter

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import sample.ktutorial.docs.flow.builders.flow.mockComputation

/*
    https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html
 */


private fun check() = runBlocking {
    val filtered = mockComputation().filter { it and 1 == 1 }

    println(filtered.count())

    filtered.collect {
        println(it)
    }
}


fun main() {
    check()
}