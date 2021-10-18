package sample.book.eokc.chp.stateflow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlin.random.Random


private fun sample() {
    fun randomPercentages(count: Int, timer: Long): Flow<Int> = flow {
        for (i in 0 until count) {
            delay(timer)
            emit(Random.nextInt(1, 100))
        }

    }

    // https://wares.commonsware.com/app/internal/book/Coroutines/page/chap-stateflow-001.html
    val job = GlobalScope.launch(Dispatchers.IO) {
        randomPercentages(100, 50).collect {
            println(it)
        }
        println("Finished GlobalScope.launch")
    }

    runBlocking {
        job.join()
    }

    println("Finished")
}

fun main() {
    sample()
}