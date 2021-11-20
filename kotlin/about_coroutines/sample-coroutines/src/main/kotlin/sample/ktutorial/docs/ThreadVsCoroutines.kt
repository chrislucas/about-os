package sample.ktutorial.docs

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import sample.helpers.calculate
import kotlin.concurrent.thread

/*
    https://kotlinlang.org/docs/coroutines-basics.html#coroutines-are-light-weight
    https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/jvm/test/guide/example-basic-06.kt
 */


private fun compareCreateManyInstanceOfThreadAndCoroutines() =
    runBlocking {

        /**
         * @see sample.ktutorial.docs.basics.scope.concurrency.manycoroutines.main
         */
        val times = 10_000


        val p = calculate {
            val str = StringBuilder()
            for (i in 1..times) {
                launch(Dispatchers.IO) {
                    str.append(i)
                }
            }
        }

        println("Timer P: $p")

        val q = calculate {
            val str = StringBuilder()
            for (i in 1..times) {
                thread {
                    str.append(i)
                }
            }
        }

        println("Timer Q: $q")

    }

private fun check() {
    println(10_0000)
    println(10_0000_000)
}

fun main() {
    compareCreateManyInstanceOfThreadAndCoroutines()
}