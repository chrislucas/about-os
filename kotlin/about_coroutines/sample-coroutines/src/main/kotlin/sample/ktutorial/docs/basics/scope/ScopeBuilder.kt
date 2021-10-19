package sample.ktutorial.docs.basics.scope

import kotlinx.coroutines.*


/*
     https://kotlinlang.org/docs/coroutines-basics.html#scope-builder
     https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html

     runblocking {} e coroutineScope {}
     - ambas sao construtoras de CoroutineScope e possuem similaridades
     como por exemplo ambas so finalizam quando as instrucoes dentro do seu 'çirpo' e
     outras coroutines filhas terminam suas execucoes
     - Entretanto possuem diferencas fundamentas
        - runblocking bloqueia a Thread atual. desbloqueando-a somente apos a execucao
        do bloco
        - coroutineScope suspende a thread, liberando as demais threads para outros usos
 */


suspend fun exec() = coroutineScope {
    launch {
        delay(1000L)
        println("Inside Launch")
    }
    println("Outside Launch")
}

private fun checkExec() = runBlocking {
    exec()
}

fun main() {
    checkExec()
}