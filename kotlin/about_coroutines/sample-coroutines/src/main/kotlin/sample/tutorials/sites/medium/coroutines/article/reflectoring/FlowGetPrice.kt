package sample.tutorials.sites.medium.coroutines.article.reflectoring

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import sample.ktutorial.logCoroutineScope
import sample.ktutorial.logThreadScope
import java.time.Instant
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.random.Random

private fun flowGetPrice(coroutineContext: CoroutineContext = EmptyCoroutineContext) {

    suspend fun getProductPrice(productId: String): Flow<Double> =
        flow<Double> {
            logCoroutineScope("5 - Before get value from Product[$productId] - [Time: ${Instant.now()}]")
            //delay(10000)
            Thread.sleep(10000)
            emit(Random.nextDouble())
            logCoroutineScope("6 - After get value from Product[$productId] - [Time: ${Instant.now()}]")
        }.flowOn(Dispatchers.IO)


    fun getFakeProductId(): Flow<String> =
        flow {
            logThreadScope("4 - Get Fake Id: [Time: ${Instant.now()}]")
            //delay(10000)
            Thread.sleep(10000)
            emit("id#1023")
        }.flowOn(Dispatchers.IO)


    fun processFakeProduct(coroutineContext: CoroutineContext = EmptyCoroutineContext) {
        runBlocking {
            logCoroutineScope("1 - Before Launch: [${Instant.now()}]")
            /*
                Starts a new coroutine
             */
            launch(coroutineContext) {
                getFakeProductId().collect { productId ->
                    getProductPrice(productId).collect { value ->
                        println("2 - Get Price: $value")
                    }
                }
            }
            logCoroutineScope("3 - After Launch: [${Instant.now()}]")
        }
    }
    processFakeProduct(coroutineContext)
    println("End ----------------------------------------------------------------------------------------")
}


private fun checkAsync() {

    /*
    CoroutineScope(CoroutineName("Meu Escopo")).launch {
        CoroutineScope(Dispatchers.Unconfined).async {
            logCoroutineScope("async")
        }
    }

     */

    runBlocking(Dispatchers.IO) {
        launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
            println("Unconfined      : I'm working in thread ${Thread.currentThread().name}")
            delay(500)
            println("Unconfined      : After delay in thread ${Thread.currentThread().name}")
        }
        launch { // context of the parent, main runBlocking coroutine
            println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
            delay(1000)
            println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
        }
    }

/*
    CoroutineScope(Dispatchers.IO).launch {
        println("launch")
    }

 */
}

fun main() {
    //flowGetPrice()
    //flowGetPrice(Dispatchers.IO)
    //flowGetPrice(Dispatchers.Unconfined)
    //flowGetPrice(CoroutineName("My Coroutine"))
    checkAsync()
}