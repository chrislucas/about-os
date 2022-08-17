package sample.tutorials.sites.kotlinlang.flow.builders

import kotlinx.coroutines.flow.asFlow

suspend fun checkRangeAsFlow() = ('a'..'z').asFlow().collect(::println)


suspend fun main() {
    checkRangeAsFlow()
}