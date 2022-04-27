package sample.ktutorial.corscopes

import kotlinx.coroutines.*
import kotlin.concurrent.timerTask
import kotlin.system.measureNanoTime

/**
 * https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/
 *
 * https://medium.com/@elizarov/coroutine-context-and-scope-c8b255d59055
 * */

suspend fun apply(): Int = 2 + 2


private fun check() {
    runBlocking {
        delay(10000L)
        println(apply())
    }

    CoroutineScope(Dispatchers.Default).launch {
        println("hello")
    }
}

private fun checkAsynFunction() = runBlocking {
    CoroutineScope(Dispatchers.Default).async {
        delay(3000L)
        2
    }
}

fun main() {
    runBlocking {
        val time = measureNanoTime {
            val p = checkAsynFunction().await()
            println(p)
        }
        println(time)
    }
}