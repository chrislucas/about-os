package sample.ktutorial.docs.github.guide.sample.basics

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * https://github.com/Kotlin/kotlinx.coroutines/tree/master/kotlinx-coroutines-core/jvm/test/guide
 */


// https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/jvm/test/guide/example-basic-06.kt
private fun basics06() = runBlocking {
    repeat(100_000) {
        launch {
            delay(5000)
            print(".")
        }
    }
}


fun main() {
    basics06()
}