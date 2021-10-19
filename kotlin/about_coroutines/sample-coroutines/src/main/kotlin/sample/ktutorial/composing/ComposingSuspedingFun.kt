package sample.ktutorial.composing

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/*
    https://kotlinlang.org/docs/composing-suspending-functions.html

 */


suspend fun A (): Int {
    delay(1000L)
    return 13
}

suspend fun B(): Int {
    delay(1000L)
    return 32
}

fun executeSeqSuspendFun() = runBlocking {
    val time = measureTimeMillis {
        val sum = A() + B()
        println("Result: $sum")
    }

    println("Time spent: $time")
}

/*
    https://kotlinlang.org/docs/composing-suspending-functions.html#concurrent-using-async
    Conceitualmente a funcao async e uma construtora de coroutines assim como a launch. Ela inicia
    uma nova coroutine (uma light-weight Thread) que trabalha concorrentmente com todas as demais
    coroutines.

    launch {} :  retorna um Job e nao carrega nenhum resultado apos execucao

    async {}:  retorna uma instancia de Deferred - 'Light-weight non-blocking Future'
    que representa uma 'Promise' que provera um resultado ao fim da execuçãp da coroutine.

    // val deferred: Deferred<Int> = async { 0 }
    Deferred:
        - possui o metodo await() para recuperar o resultado
        - eh uma subclasse de Job, podendo ser cancelado

    a funcao de exemplo abaixo vai funcionar 2x mais rapudo pois as
    coroutines vao ser executadas concorrentemente

*/
fun executeAsyncSuspendFun() = runBlocking {
    val time = measureTimeMillis {
        // Redundant 'async' call may be reduced to 'kotlinx.coroutines.withContext'
        // sugestao de refactoring da IDE

        // async { A() }.await()
        val p =  async { A() } // withContext(Dispatchers.Default) { A() }

        // async { B() }.await()
        val q = async { B() } // withContext(Dispatchers.Default) { B() }

        println("Result: ${p.await() + q.await()}")
    }

    println("Time spent: $time")
}

fun main() {
    //executeSeqSuspendFun()
    executeAsyncSuspendFun()
}
