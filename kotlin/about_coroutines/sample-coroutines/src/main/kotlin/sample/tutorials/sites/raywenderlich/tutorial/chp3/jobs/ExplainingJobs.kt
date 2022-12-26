package sample.tutorials.sites.raywenderlich.tutorial.chp3.jobs

import kotlinx.coroutines.*
import sample.ktutorial.logCoroutineScope

/*
    https://www.raywenderlich.com/books/kotlin-coroutines-by-tutorials/v2.0/chapters/3-getting-started-with-coroutines#toc-chapter-007-anchor-005

    Quando lancamos uma coroutine via a coroutine builder launcher,
    pedimos ao sistema para executar o trecho de codigo passado via
    funcao lambda que ela recebe como argumento.

    Esse codigo entra numa fila para ser executado posteriormente

    A funcao launch nos retorna uma instancia de Job.

    Um Job é um handler, um "manipulador" de coroutines
    que esteja numa fila. Apesar de possuir poucos metodos e atributos essa classe
    prove muita extensibilidade, por exemplo a possibilidade de criar dependencia
    entre diferentes Jobs usando o metodo join()

        - Se o Job A invocar o metodo join() no Job , o primeiro nao sera executa ate que
        o segundo tenha completado

        - Tambem eh possivel criar uma relacao de PAI-FILJO entre instancias de Jobs


 */


private fun checkFunWithJob() {

    fun testA() {
        runBlocking {
            val scope = CoroutineScope(Dispatchers.IO)
            val message = "${0x0f} | ${0b1111}"
            with(scope) {
                val job1 = launch {
                    logCoroutineScope("Primeiro launch: $message")
                }

                launch {
                    logCoroutineScope("Segundo launch: $this")
                    // esse print nao ocorre por conta da primeira chamada
                    println("Println($message)")
                }

                // Por que o print ocorre aqui ?
                launch {
                    println("Terceiro Launch - Println($message)")
                }
                // e o primeiro log só ocorre quando eu executo um join
                //job1.join()
            }
        }

    }

    fun testB() {
        val scopeA = CoroutineScope(Dispatchers.IO)
        val scopeB = CoroutineScope(Dispatchers.IO)
        val scopeC = CoroutineScope(Dispatchers.IO)
        val message = "${0x0f} | ${0b1111}"

        with(scopeA) {
            launch {
                logCoroutineScope("Escopo A: $message")
            }
        }


        with(scopeB) {
            launch {
                logCoroutineScope("Escopo B: $message")
                println("Escopo B Println($message)")
            }
        }

        with(scopeC) {
            launch {
                println("Escopo C Println($message)")
            }
        }

        with(scopeA) {
            launch {
                println("Escopo A 2-th Println($message)")
            }
        }
    }

    testB()
}


/*
    Teste vinculando Jobs diferentes
 */

private fun depedencyBetweenJobs() {

    fun withStartCoroutineStart(start: CoroutineStart = CoroutineStart.LAZY) {
        runBlocking {
            val a = CoroutineScope(Dispatchers.IO).launch(start = start) {
                logCoroutineScope("Launch A")
            }

            CoroutineScope(Dispatchers.IO).launch {
                logCoroutineScope("Launch B")
                a.join()
                logCoroutineScope("Launch B")
            }.join()
        }
    }

    fun launchManyCoroutinesSequencially() {
        runBlocking {
            val a = CoroutineScope(Dispatchers.IO).launch {
                logCoroutineScope("Launch A")
            }

            CoroutineScope(Dispatchers.IO).launch {
                logCoroutineScope("Launch B")
            }.join()
        }
    }

    withStartCoroutineStart(CoroutineStart.LAZY)
    println("*********************************************** FIRST ************************************************\n")
    withStartCoroutineStart(CoroutineStart.DEFAULT)
    println("*********************************************** SECOND *************************************************\n")
    withStartCoroutineStart(CoroutineStart.UNDISPATCHED)

    println("*********************************************** LAST *************************************************\n")
    launchManyCoroutinesSequencially()
}

fun main() {
    depedencyBetweenJobs()
}