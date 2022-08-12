package sample.tutorials.klogging

import io.klogging.logger
import kotlinx.coroutines.runBlocking


suspend fun whatever() {
    logger<Int>().info("Test %d", 2)
}


fun main() {
    runBlocking {
        whatever()
    }
}