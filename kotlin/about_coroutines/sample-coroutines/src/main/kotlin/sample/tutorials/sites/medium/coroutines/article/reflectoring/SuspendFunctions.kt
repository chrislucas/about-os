package sample.tutorials.sites.medium.coroutines.article.reflectoring

import kotlinx.coroutines.*
import sample.ktutorial.logCoroutineScope
import sample.ktutorial.logThreadScope
import java.time.Instant
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.random.Random


/*
    https://reflectoring.io/understanding-kotlin-coroutines-tutorial/

    - "suspending function is the main building block of a coroutine"
        - A thread executando uma funcao regular bloqueia ooutras funcoes de executarem ate a primeira
        funcao terminar sua execucao
        - Para nao ter perda de performance por conta do bloqueio feito pela thread foi projetada a suspending
        function, essa sendo chamada dentro do escopo de uma coroutine.
            - Ao chamar uma suspending function, ela sera pausada/suspensa permitindo que a thread a libere para
            que outra atividade seja executa.
            - A funcao pausada/suspensa pode ser recuperada para ser executada na mesma thread ou numa outra
 */


private  fun checkSuspendFunction(coroutineContext: CoroutineContext = EmptyCoroutineContext) {

    suspend fun getProductPrice(productId: String): Double {
        logCoroutineScope("5 - Before get value from Product[$productId] - [Time: ${Instant.now()}]")
        delay(10000)
        logCoroutineScope("6 - After get value from Product[$productId] - [Time: ${Instant.now()}]")
        return Random.nextDouble()
    }

    fun getFakeProductId(): String {
        logThreadScope("4 - Get Fake Id: [Time: ${Instant.now()}]")
        return "id#1023"
    }

    fun processFakeProduct(coroutineContext: CoroutineContext = EmptyCoroutineContext) {
        runBlocking {
            logCoroutineScope("1 - Before Launch: [${Instant.now()}]")
            /*
                Starts a new coroutine
             */
            launch (coroutineContext) {
                println("2 - Get Price: ${getProductPrice(getFakeProductId())}")
            }
            logCoroutineScope("3 - After Launch: [${Instant.now()}]")
        }
    }
    processFakeProduct(coroutineContext)
    println("End ----------------------------------------------------------------------------------------")
}

fun main() {
    //checkSuspendFunction()
    //checkSuspendFunction(Dispatchers.IO)
    //checkSuspendFunction(Dispatchers.Unconfined)
    checkSuspendFunction(CoroutineName("My Coroutine"))
}