package sample.ktutorial.docs.sharedmutablestate

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

/**
 * https://kotlinlang.org/docs/shared-mutable-state-and-concurrency.html
 *
 * Shared mutable state and concurrency
 *
 * Coroutines podem ser executadas paralelamente usando um multi-thread Dispatcher.Default.
 *
 * - Isso apresenta os problemas usuais de paralelismo
 * - O princiapl problema e com a sincronizacao do acesso a "Estados mutaveis compartilhados"
 * - Algumas solucoes para esse problema em coroutine sao similares as solucoes existentes no
 * mundo multi-thread, outras sao solucoes unicas
 */


suspend fun fakeMassiveAction(action: suspend () -> Unit) {
    val n = 100
    val k = 1000
    val time = measureTimeMillis {
        coroutineScope {
            repeat(n) {
                launch {
                    repeat(k) {
                        action()
                    }
                }
            }
        }
    }

    println("Completed (${n*k}) actions in $time ms")
}

private suspend fun mapCoroutineInfo(message: String): Pair<String, String> {
    return with(Thread.currentThread()) {
        Pair(name, "Message: $message\nInfo: ${currentCoroutineContext()}")
    }
}

private fun checkFakeMassiveAction() {
    runBlocking {
        withContext(Dispatchers.Default) {
            fakeMassiveAction { 0xff and 0xfa }
        }

        withContext(Dispatchers.IO) {
            fakeMassiveAction { 0xff and 0xfa }
        }
    }
}

/*
    Entendendo o erro de compreensao sobre a anotacao corrigir questoes de sincronismo
    em sistemas multithread.
    https://kotlinlang.org/docs/shared-mutable-state-and-concurrency.html#volatiles-are-of-no-help
    Volatile nao ajuda
 */
@Volatile
var mutableValue = 0


private fun tryWithSharedMutableVariable() {
    runBlocking {
        //var mutableValue = 0
        val groupCoroutineInfo = mutableMapOf<String, MutableList<String>>()
        // Coroutines podem ser executadas paralelamente usando um multi-thread Dispatcher.Default.
        withContext(Dispatchers.Default) {
            fakeMassiveAction {
                /**
                 * No primeiro exemplo do tutorial temos um algoritmo que adiciona 1 a variavel counter
                 * (chamamos aqui de mutableValue) e ela na leva a anotacao @Volatile. Como
                 * centenas de coroutines sem sincronizacao acabam acessando o seu valor e modificando-o, mesmo
                 * que a lambda passada pra fakeMassiveAction execute N vezes (ate o momento
                 * dessa anotacao N = 100000) a soma da variavel nao chegara a N provavelmente
                 */
                mutableValue += 1
                val (k, v) = mapCoroutineInfo("Contando numa coroutine $mutableValue")
                if (groupCoroutineInfo[k] == null) {
                    groupCoroutineInfo[k] = mutableListOf()
                }
                groupCoroutineInfo[k]?.add(v)
            }
        }

        val map: Map<String, Int> = groupCoroutineInfo.map { (k, v) -> k to v.size }
            .associate { it }
        println(map)
        println("Soma das Listas: ${map.values.sum()}")
        println("Resultado Final: $mutableValue")
    }
}

/**
 * https://kotlinlang.org/docs/shared-mutable-state-and-concurrency.html#thread-safe-data-structures
 *
 * Package java.util.concurrent.atomic
 * https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/atomic/package-summary.html
 *
 * "Um pequeno grupo de classes (toolkit) que suporta lock-freethread-safe programming sobre variaveis únicas
 * (int, boolean, double,)'"
 *
 * AtomicInteger
 * https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/atomic/AtomicInteger.html
 */
private fun threadSafeDataStructure() = runBlocking {
    val counter = AtomicInteger()
    val groupCoroutineInfo = mutableMapOf<String, MutableList<String>>()
    withContext(Dispatchers.Default) {
        fakeMassiveAction {
            counter.incrementAndGet()
            val (k, v) = mapCoroutineInfo("Contando numa coroutine $mutableValue")
            if (groupCoroutineInfo[k] == null) {
                groupCoroutineInfo[k] = mutableListOf()
            }
            groupCoroutineInfo[k]?.add(v)
        }
    }
    val map: Map<String, Int> = groupCoroutineInfo.map { (k, v) -> k to v.size }
        .associate { it }
    println(map)
    println("Soma das Listas: ${map.values.sum()}")
    println("Resultado Final: $counter")
}

fun main() {
    threadSafeDataStructure()
}