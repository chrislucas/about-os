package sample.ktutorial.docs.sequence

import kotlinx.coroutines.*
import sample.ktutorial.docs.logCoroutineScope

/*
    https://kotlinlang.org/docs/flow.html#sequences
 */

/*
    Abaixo temos uma simulacao de uma funcao que computa algo e demora 1s para
    executar essa tarefa. Essa eh uma operacao que consome processamento da CPU
 */
private fun simple() = sequence {
    logCoroutineScope("simple function")

    fun operation() {
        println("Start Operation")
        Thread.sleep(1000L)
        println("Finish Operation")
    }
    for (i in 0..10) {
        operation() // executa uma operacao fake
        yield("simple function: $i")    // retorna um valor
    }
}

private suspend fun suspendSimpleFunction() = sequence {
    logCoroutineScope("SuspendSimple function")
    fun operation() {
        println("Start Operation")
        Thread.sleep(1000L)
        println("Finish Operation")
    }
    for (i in 0..10) {
        operation() // executa uma operacao fake
        yield("simple function: $i")    // retorna um valor
    }
}


private fun anotherSimple() = sequence {
    logCoroutineScope("AnotherSimple function")

    suspend fun operation() {
        println("Start Operation")
        delay(1000L)
        println("Finish Operation")
    }

    val scope = CoroutineScope(Dispatchers.IO)
    for (i in 0..10) {
        scope.launch { operation() }
        //runBlocking (Dispatchers.IO) { operation() }
        yield("another simple function: $i")
    }
}

/*
    https://kotlinlang.org/docs/flow.html#suspending-functions
    Implementando a mesma coisa das funcoes acima que usando a funcao
    builder sequence {}, porem essas funcoes implementam uma computacao
    que bloqueia a MainThread.
    Podemos marcara essa funcao com o modificador suspend assim ela pode executar sem
    bloquear a MainThread e retornar o seu resultado, no caso de exemplo uma lista
    de inteiros
 */
private suspend fun suspendSimpleOpInList(): List<Int> {
    logCoroutineScope("suspendSimpleOpInList function")

    delay(1000L)
    return (0..10).toList()
}


private fun checkingSimpleFunction() = runBlocking {
    simple().forEach {
        println(it)
    }
}

private fun checkingAnotherSimpleFunction() = runBlocking {
    anotherSimple().forEach {
        println(it)
    }
}

private fun checkSuspendSimpleOpInList() = runBlocking(Dispatchers.IO) {
    suspendSimpleOpInList().forEach {
        println(it)
    }
}

private fun checkSuspendSimpleFunction() = runBlocking {
    suspendSimpleFunction().forEach {
        println(it)
    }
}

fun main() {
    //checkingSimpleFunction()
    //checkingAnotherSimpleFunction()
    checkSuspendSimpleOpInList()
    //checkSuspendSimpleFunction()
}