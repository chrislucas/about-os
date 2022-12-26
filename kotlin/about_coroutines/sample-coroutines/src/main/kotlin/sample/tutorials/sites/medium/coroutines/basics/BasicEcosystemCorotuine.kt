package sample.tutorials.sites.medium.coroutines.basics

import kotlinx.coroutines.*
import sample.ktutorial.logCoroutineScope
import sample.ktutorial.about

/*
    https://medium.com/mobile-app-development-publication/kotlin-coroutine-scope-context-and-job-made-simple-5adf89fcfe94

    O artigo acima descreve as consequencias da relacao parent-child entre Jobs
    de coroutines lançadas uma dentro da outra. As consequencias sao

    - A coroutine filha herda o contexto da pai
    - A coroutine pai e suspensa ate que as filhas sejam finalizadas
    - Quando a coroutine pai eh cancelada suas filhas também são
    - Quando uma coroutine filha e destroida, também destroi-se a coroutine pai
 */


private fun checkParentChildRelation() {
    runBlocking(CoroutineName("Sample")) {
        logCoroutineScope("Name: ${coroutineContext[CoroutineName]?.name}")

        val s = launch {
            delay(1000)
            logCoroutineScope("Name: ${coroutineContext[CoroutineName]?.name} [inside]")
        }

        //s.cancel()

        launch(CoroutineName("Inside Launch")) {
            delay(1000)
            logCoroutineScope("Name: ${coroutineContext[CoroutineName]?.name}")
        }


        try {
            val job = launch(CoroutineName("Dangerous Launch")) {
                delay(1000)
                throw Exception("A singular error")
            }

            /*
                Se um Job for cancelado ou falhar no estado Active ou Completing, seu estado muda
                para Cancelling. Nesse estado temos a ultima chance para limpar a bagunca, desfazendo
                conexoes em aberto, liberando recursos ou qualquer outra coisa que precise ser feita

             */
            with(job) {
                invokeOnCompletion {
                    if (isCancelled) {
                        println("Cancelled: $it")
                    }
                }
            }
        } catch (e: RuntimeException) {
            println(e)
        }


        val lazily = launch(CoroutineName("Inside Launch Lazily"), start = CoroutineStart.LAZY) {
            delay(1000)
            logCoroutineScope("Name: ${coroutineContext[CoroutineName]?.name}")
        }

        with(lazily) {
            join()
            invokeOnCompletion {

            }

        }
    }
}


/*
    Jobs permitem
        - cancelar coroutines
        - rastrear seu estado
     O ciclo de vida de um Job
     new -> active ->
     ([status=complete]completing, [status=finish]completed) ou ([cancel/fail]cancellng, [finish]cancelled)
 */
private suspend fun checkStateOfJob() {
    coroutineScope {
        val breakLine = "*****************************************************************"
        val jobA = Job()
        logCoroutineScope("Job A: ${jobA.about()}")
        jobA.complete()
        logCoroutineScope("After complete Job A: ${jobA.about()}")
        println(breakLine)

        val jobB = launch {
            delay(2000)
        }
        with(jobB) {
            logCoroutineScope("Job B: $${this.about()} 1s delayed")
            join() // join to await coroutine completion
            logCoroutineScope("After Join Job B: $${this.about()} 1s delayed")
        }
        println(breakLine)


        val lazyJobC = launch(start = CoroutineStart.LAZY) {
            delay(3000)
        }
        with(lazyJobC) {
            logCoroutineScope("Before start Lazily Job: ${this.about()}")
            start()
            logCoroutineScope("After Start Lazily Job: ${this.about()}")
            join()
            logCoroutineScope("After Join Lazily Job: ${this.about()}")

        }
        println(breakLine)

    }
}


/*
    start e join methods
    join
    https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/join.html

    start
    https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/start.html
 */

private suspend fun checkCancellableJobLifeCycle() {
    coroutineScope {
        val jobCancellableA = launch {
            delay(5000)
        }
        with(jobCancellableA) {
            logCoroutineScope("Before start Cancellable Job: ${this.about()}")
            start()
            logCoroutineScope("After start Cancellable Job: ${this.about()}")

            cancel("Cancelando ...")
            logCoroutineScope("After cancel Cancellable Job: ${this.about()}")

            invokeOnCompletion {
                println("OnCompletion: ${this.about()}")
            }
        }

        val jobCancellableB = launch {
            delay(5000)
        }

        println("***************************************************************************************************\n")

        with(jobCancellableB) {
            //logCoroutineScope("Before start Cancellable Job: ${this.about()}")
            //start()
            //logCoroutineScope("After start Cancellable Job: ${this.about()}")

            logCoroutineScope("Before join Cancellable Job: ${this.about()}")

            join()
            logCoroutineScope("After join Cancellable Job: ${this.about()}")

            cancel("Cancelando ...")
            logCoroutineScope("After cancel Cancellable Job: ${this.about()}")

            invokeOnCompletion {
                println("OnCompletion: ${this.about()}")
            }
        }
    }
}

fun main() {
    runBlocking {
        checkCancellableJobLifeCycle()
    }
}