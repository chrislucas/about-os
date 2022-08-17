package sample.tutorials.sites.kotlinlang.flow.builders

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf


suspend fun checkRangeFlowOf() = flowOf(('a'..'z').asFlow()).collect(::println)


suspend fun main() {
    checkRangeAsFlow()
}