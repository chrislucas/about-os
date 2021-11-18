package sample.ktutorial.docs.flow.builders.flow.operator.terminal.single

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking


private fun check() = runBlocking {
    val p = flow { emit(1) }
        .single()
    println(p)
}


fun main() {
    check()
}