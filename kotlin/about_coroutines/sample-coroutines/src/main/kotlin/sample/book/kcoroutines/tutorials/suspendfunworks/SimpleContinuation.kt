package sample.book.kcoroutines.tutorials.suspendfunworks

import kotlinx.coroutines.runBlocking
import kotlin.coroutines.suspendCoroutine

/**
 * https://kt.academy/article/cc-suspension
 */

suspend fun test(): Int {
    return suspendCoroutine { continuation ->
        println("$continuation\n")
        continuation.resumeWith(Result.success(1))
        println("$continuation")
    }
}


fun main() {
    runBlocking {
        println("$this")
        test()
    }
}