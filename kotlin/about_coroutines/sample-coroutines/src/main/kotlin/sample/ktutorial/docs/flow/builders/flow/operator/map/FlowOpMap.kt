package sample.ktutorial.docs.flow.builders.flow.operator.map

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import sample.ktutorial.docs.flow.builders.flow.mockComputation


@OptIn(InternalCoroutinesApi::class)
private fun check() = runBlocking {
    val collector = object : FlowCollector<String> {
        override suspend fun emit(value: String) {
            println(value)
        }
    }
    mockComputation()
        .map { if (it and 1 == 0) "Even" else "Odd" }
        //.collect {   }
        .collect(collector)
}

fun main() {
    check()
}