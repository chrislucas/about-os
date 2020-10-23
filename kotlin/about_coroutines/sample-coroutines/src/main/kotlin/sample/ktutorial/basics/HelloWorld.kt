package sample.ktutorial.basics

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * https://kotlinlang.org/docs/reference/coroutines/coroutines-guide.html
 * */

fun main() {

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
        val p = suspend { println(0xff) }
        p.invoke()
    }

    println(job)

}