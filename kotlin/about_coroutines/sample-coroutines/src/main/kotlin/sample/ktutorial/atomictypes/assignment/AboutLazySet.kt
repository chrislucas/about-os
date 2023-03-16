package sample.ktutorial.atomictypes.assignment

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import sample.ktutorial.logCoroutineScope
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/*
    https://www.baeldung.com/java-atomic-set-vs-lazyset

    O tutorial nos mostra a diferenca entre as funcoes set e lazySet nos objetos
    AtomicInteger e AtomicReference

    Sobre AtomicReference
    https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/atomic/AtomicReference.html

    Atomic Variables
    - Atomic Variables permitem que executemos operacoes thread-safe em referencias de clases
    ou atributos/field sem ter que adicionar monitors ou mutexes
 */

private fun checkSetWithThread() {
    /*
        O metodo set eh o equivalente a escrever num atributo volatile
        Apos chamar a funcao set, acessamos a variavel oma a funcao get numa
        tread diferente, a mudanca e visivel imediatamente. Isso significa
        que o valor for liberado do cache CPU para a camada de memoria comum
        a todos os cores do CPU

     */
    val atomicValue = AtomicInteger(-1)
    thread {
        repeat(10) {
            atomicValue.set(it)
            println("Set: $it")
            Thread.sleep(1000L)
        }
    }

    thread {
        repeat(10) {
            synchronized(atomicValue) {
                println("Get: ${atomicValue.get()}")
            }
            Thread.sleep(1000L)
        }
    }
}

private fun checkSetWithCoroutine(coroutineContext: CoroutineContext = Dispatchers.IO) = runBlocking {
    val atomicValue = AtomicInteger(-1)
    launch(coroutineContext) {
        repeat(10) {
            atomicValue.set(it)
            logCoroutineScope("Set: $it")
            delay(1000L)
        }
    }

    launch(coroutineContext) {
        repeat(10) {
            logCoroutineScope("Get: ${atomicValue.get()}")
            delay(1000L)
        }
    }
}

private fun checkCheckSetWithCoroutine() {
    checkSetWithCoroutine(Dispatchers.Unconfined)
    checkSetWithCoroutine(EmptyCoroutineContext)
}

private fun checkSetWithCoroutineSynchronized(coroutineContext: CoroutineContext = Dispatchers.IO) = runBlocking {
    val atomicValue = AtomicInteger(-1)
    launch(coroutineContext) {
        repeat(10) {
            atomicValue.set(it)
            logCoroutineScope("Set: $it")
            delay(1000L)
        }
    }

    launch(coroutineContext) {
        repeat(10) {
            synchronized(atomicValue) {
                println("Get: ${atomicValue.get()}")
            }
            delay(1000L)
        }
    }
}

private fun checkCheckSetWithCoroutineSynchronized() {
    checkSetWithCoroutineSynchronized(Dispatchers.Unconfined)
    checkSetWithCoroutineSynchronized(Dispatchers.Default)
}

fun main() {
    checkSetWithThread()
}