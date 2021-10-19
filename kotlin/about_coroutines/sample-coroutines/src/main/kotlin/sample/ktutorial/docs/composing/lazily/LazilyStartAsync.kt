package sample.ktutorial.docs.composing.lazily

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/*
    https://kotlinlang.org/docs/composing-suspending-functions.html#lazily-started-async

    Podemos iniciar uma coroutine de forma atrasada atraves do arugmento start da funcao async
    passando o valor  CoroutineStart.LAZY
        - nessa configuracao a coroutine so e iniciada quando o resultado dela for
        requisitado pela funca await ou pela funcao star
 */


private suspend fun doA(): Int {
    delay(1000)
    return 12
}

private suspend fun doB(): Int {
    delay(1000)
    return 15
}

private fun executeOnStart() = runBlocking {
    val timer = measureTimeMillis {
        val p = async(start = CoroutineStart.LAZY) { doA() }
        val q = async(start = CoroutineStart.LAZY) { doB() }
        p.start()
        q.start()
        val sum = p.await() + q.await()
        println("Sum: $sum")
    }
    println("Time: $timer")
}

/*
    abaixo temos um exemplo onde nao chamados a funcao start
    somente a funcao await

    O resultado eh que obtemos uma execucao sequencial ads coroutines
    uma vez que a funcao await() vai esperar a execucao da coroutine
    para obter o resultado.

    fazendo dessa forma temos uma execucao sequencial da funcao
    o que eh muito pior em termos de desempenho
 */
private fun executeOnAwait() = runBlocking {
    val timer = measureTimeMillis {
        val p = async(start = CoroutineStart.LAZY) { doA() }
        val q = async(start = CoroutineStart.LAZY) { doB() }

        val sum = p.await() + q.await()
        println("Sum: $sum")
    }
    println("Time: $timer")
}


@OptIn(ExperimentalCoroutinesApi::class)
private fun executeOnAwaitStartAtomic() = runBlocking {
    val timer = measureTimeMillis {
        val p = async(start = CoroutineStart.ATOMIC) { doA() }
        val q = async(start = CoroutineStart.ATOMIC) { doB() }
        val sum = p.await() + q.await()
        println("Sum: $sum")
    }
    println("Time: $timer")
}


private fun executeOnAwaitStartDefault() = runBlocking {
    val timer = measureTimeMillis {
        val p = async(start = CoroutineStart.DEFAULT) { doA() }
        val q = async(start = CoroutineStart.DEFAULT) { doB() }
        val sum = p.await() + q.await()
        println("Sum: $sum")
    }
    println("Time: $timer")
}



private fun executeOnAwaitStartUndispatched() = runBlocking {
    val timer = measureTimeMillis {
        val p = async(start = CoroutineStart.UNDISPATCHED) { doA() }
        val q = async(start = CoroutineStart.UNDISPATCHED) { doB() }
        val sum = p.await() + q.await()
        println("Sum: $sum")
    }
    println("Time: $timer")
}

fun main() {
    executeOnAwait()
    println("**************************************")
    executeOnStart()
    println("**************************************")
    executeOnAwaitStartAtomic()
    println("**************************************")
    executeOnAwaitStartDefault()
    println("**************************************")
    executeOnAwaitStartUndispatched()
}