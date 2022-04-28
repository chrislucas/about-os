package sample.book.kcoroutines.tutorials.suspendfunworks


import kotlinx.coroutines.*
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


/**
 * https://kt.academy/article/cc-suspension
 *
 * Esse exemplo tem um resultado nao intuitivo
 *  - "antes", "suspenso", "depois", "retornando"
 *  - a String "depois" eh impressa antes da String "retornando"
 *  Esse exemplo contra intuitivo é importante estudar.
 *
 *   A ordem de execucao
 *      - executa-se algo antes da suspensao da coroutine
 *      - executa-se A Thread que está dentro dda funcao lambda passada para suspensao da coroutine
 *      - após o retorno da coroutine (resume), executa-se o restando do código fora da funcao lambda
 *      - executa-se o restante do código da thread
 */

suspend fun sampleSuspendCoroutineCallingThread() {
    println("Antes")
    suspendCoroutine<Unit> { continuation ->
        thread {
            println("Suspenso")
            Thread.sleep(3000L)
            continuation.resume(Unit)
            println("Retornado")
        }
    }
    println("Depois")
}

private fun continuationAfterSeconds(continuation: Continuation<Unit>) {
    thread {
        println("Suspenso $continuation")
        Thread.sleep(1000L)
        continuation.resume(Unit)
        println("Retornado $continuation")
        println("Ainda executando a Thread 1")
        println("Ainda executando a Thread 2")
        println("Ainda executando a Thread 3")
        println("Ainda executando a Thread FIM")
    }
}

private suspend fun call() {
    println("Antes")
    suspendCoroutine<Unit> {
        continuationAfterSeconds(it)
    }
    println("Depois")
}

suspend fun main() {

    /*
        CoroutineScope(Dispatchers.IO).launch {
            sampleSuspendCoroutineCallingThread()
        }
     */
/*
    runBlocking {
        sampleSuspendCoroutineCallingThread()
    }

 */

    //sampleSuspendCoroutineCallingThread()

    call()
}