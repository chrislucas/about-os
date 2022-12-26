package sample.tutorials.sites.medium.coroutines.basics

import kotlinx.coroutines.*
import sample.ktutorial.childrenToString
import sample.ktutorial.logCoroutineScope

/*
    https://kt.academy/article/cc-job
 */


private fun brakeStrucuredConcurrencyMechanism() {
    runBlocking {

        launch(CoroutineName("Child 0")) {
            delay(1000)
            logCoroutineScope("Just a just - coroutine 0")
        }

        // Novo Job/CoroutineContext substituindo o contexto
        launch (Job()) {
            delay(1000)
            // o trecho abaixo nao sera executado
            logCoroutineScope("Launching coroutine 1 inside runblocking")
        }

        /*
            O mecanismo de concorrencia nao ira funcionar se num Novo Job/CoroutineContext substituir
            o contexto da coroutine pai.
            No exemplo acima, a coroutine criada por runBlocking nao esperará a sua coroutine filha criada por launch
            pois nao tem nenhuma relacao de contexto com a coroutine pai
         */

        logCoroutineScope("Parent Coroutine: ${coroutineContext.job.childrenToString()}")
    }
}


fun checkSuspendingFunction() {

    runBlocking {
        /*
            Como fazer com que a coroutine pai espere pela coroutine filha ? fonte: https://kt.academy/article/cc-job

            A primeira vantagem da existencia do Job eh que ele pode ser usado para fazer esperar ate que uma coroutine
            seja completada.
            Para isso usamos o metodo join

         */
        launch  {
            delay(1000)
            // o trecho abaixo nao sera executado
            logCoroutineScope("Launching coroutine/Job 1 inside runblocking")
        }.join()

        launch {
            delay(2000)
            logCoroutineScope("Launching coroutine/Job 2 inside runblocking")

        }.join()

        logCoroutineScope("Parent Coroutine: ${coroutineContext.job.childrenToString()}")
    }

}


fun main() {
    //brakeStrucuredConcurrencyMechanism()
    checkSuspendingFunction()
}