package sample.tutorials.sites.kotlinlang.coroutine.async

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


private fun checkAsyncBehaviorInsideRunblocking() : Int  = runBlocking {

    val p =  async {
        delay(10000L)
        2
    }

    val q = async {
        delay(1000L)
        40
    }

    p.await() + q.await()
}


fun main() {
    println(checkAsyncBehaviorInsideRunblocking())
}