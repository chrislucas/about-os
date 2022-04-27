package sample.book.kcoroutines.tutorials.flow


import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

/**
 * https://kt.academy/article/how-flow-works
 */

private fun check() {
    runBlocking {
        val r = flow<Int> {
            emit(1)
        }
        println(r.collect())
    }
}

private fun start() {
    // function type () -> Unit
    val f = {
        println("f() = A B C")
    }
    f()
    f()
    println("************************")

    // function type
    val g: ((String) -> Unit) -> Unit = { emit ->
        emit("g(ABC)")
    }
    g { println(it) }
    //g {}
    println("************************")
    val h: (String, (String) -> Unit) -> Unit = { arg, emit ->
        emit("h($arg)")
    }
    h("ABC") { println(it) }
    h("DEF") { println(it) }
}

fun main() {
    start()
}