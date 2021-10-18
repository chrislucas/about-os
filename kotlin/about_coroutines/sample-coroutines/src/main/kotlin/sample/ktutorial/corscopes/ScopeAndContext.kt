package sample.ktutorial.corscopes

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

fun sampleGlobalScopeLaunch() {
    val job = GlobalScope.launch {

        // https://kotlinlang.org/docs/reference/lambdas.html#lambda-expression-syntax
        val fn: (Unit, CoroutineContext.Element) -> Unit = {
                _, e ->
            println("$e")
        }
        coroutineContext.fold(Unit, fn)
        //suspend { coroutineContext.fold(Unit, fn) }.invoke()
    }

    runBlocking {
        job.join()
    }

    println(job)

}

fun main() {
    sampleGlobalScopeLaunch()
}