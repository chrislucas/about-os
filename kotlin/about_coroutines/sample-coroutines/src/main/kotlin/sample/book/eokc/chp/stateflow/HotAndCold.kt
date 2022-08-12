package sample.book.eokc.chp.stateflow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random


/**
 * Channel -> hot
 * Flow -> Cold
 *
 * Qual a diferenca entre hot e cold stream ?
 * De forma simples, um produtor que fornece dados de forma ininterrupta mesmo que nao
 * tem um consumidor, este é chamado de HOT. Ja o produtor que soomente fornece
 * dados quando solicitado, esse é chamado de COLD
 */

private fun sample() {
    fun producerRandomNumbers(count: Int, timer: Long): Flow<Int> = flow {
        for (i in 0 until count) {
            delay(timer)
            emit(Random.nextInt(1, 100))
        }
    }

    // https://wares.commonsware.com/app/internal/book/Coroutines/page/chap-stateflow-001.html
    val job = GlobalScope.launch(Dispatchers.IO) {
        producerRandomNumbers(100, 50).collect {
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