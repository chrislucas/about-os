package sample.ktutorial.basics

import kotlinx.coroutines.*

/**
 * Guide
 * https://kotlinlang.org/docs/reference/coroutines/coroutines-guide.html
 * https://github.com/Kotlin/kotlinx.coroutines/blob/master/ui/coroutines-guide-ui.md
 *
 * Basics
 * https://kotlinlang.org/docs/coroutines-basics.html#your-first-coroutine
 *
 * Resumo
 *
 * 1) Definicao - Coroutine eh uma instancia de uma processo computacional que pode ser suspendido. Conceitualmente
 * similar a uma Thread, quando olhamos do ponto em que tomamos um bloco de codigo e exeuctamos ESPECIFICO e o executamos
 * de forma concorrente com o restante do codigo
 *
 * A coroutine nao esta ligada a nenhuma thread
 *
 * Podemos
 *
 * */


@OptIn(DelicateCoroutinesApi::class)
private fun simpleHelloWorldDelayed() {
    /**
    CoroutineScope.launch(CoroutineContext = EmptyCoroutineContext, CoroutineStart = CoroutineStart.DEFAULT
    , suspend CoroutineScope.() -> Unit)

    lanca uma nova coroutine

    launch: Um construtor de coroutines. kanca uma coroutine concorrentemente com o resto do codigo
    a ser executado a sua volta. O codigo dentro do bloco da coroutine sera executado de forma independente


     * */
    GlobalScope.launch {
        delay(1000) // aplica um delay nao bloqueante
        // delay: Uma suspending functiopn especial, Interrompe a coroutine por um tempo determinado

        print("World")
    }

    print("Hello ")
    Thread.sleep(2000)
}

@OptIn(DelicateCoroutinesApi::class)
private fun sample2() {
    /**
     * GlobalScope : CoroutineScope
     *
     * Representa uma CoroutineScope sem vinculo com alguma instancia de Job.
     *
     * Resumn da doc para meu entendimento.
     *
     * GlobalScope sao usadas para iniciar coroutines denominadas de top-level. Essas sao operacoes
     * que perduram por to-do tempo de vida da aplicacao (lifecycle application)
     *
     * */
    val job = GlobalScope.launch {
        withContext(this.coroutineContext) {
            suspend { println(0xff) }.invoke()
        }
    }

    /**
     *
     * */
    runBlocking {
        /**
         * interface Job: Elenent
         *
         * */
        job.join()
    }

    println(job)
}

fun exec1() = runBlocking {
    /*
*       https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html
*   */
    val job1 = CoroutineScope(Dispatchers.Default).launch {
        launch {
            delay(1000L)
            print(" chris")
        }
    }

    /*
        runblocking: Um construtor de coroutines que permite ligar
        o escopo fora do bloco de runBlocking com o bloco de dentro.

    * */

    // launch so pode ser declarada dentro de uma CoroutineScope
    val job2 = launch {
        delay(1009L)
        print(" Lucas ")
    }


    print("Ola, ")
    launch {
        delay(1010L)
        println("\nJob1: $job1\nJob2: $job2")
    }
}

fun exec2() = runBlocking {
    launch {
        delay(1000L)
        print("Lucas ")
    }
    print("Ola, ")
}

fun main() {
    exec1()
}
