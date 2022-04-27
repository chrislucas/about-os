package sample.ktutorial.docs.composing.lazily

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


fun checkCallNTimes() {
    runBlocking {
        val p = async(start = CoroutineStart.LAZY) {
            delay(5000)
            1
        }
        p.start()
        println(p.await())
        println(p.await())
    }
}

fun main() {
    checkCallNTimes()
}