package sample.ktutorial.docs.flow.builders.flow.operator.reduce

import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.runBlocking
import sample.ktutorial.docs.flow.builders.flow.mockComputation

/*
    https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html
 */


private fun check() = runBlocking {
    val sum = mockComputation().reduce { acc: Int, value: Int-> acc + value }

    println(sum)
}


fun main() {
    check()
}