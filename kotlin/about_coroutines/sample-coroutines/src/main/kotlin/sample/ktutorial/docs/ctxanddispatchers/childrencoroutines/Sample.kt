package sample.ktutorial.docs.ctxanddispatchers.childrencoroutines

import kotlinx.coroutines.*

/*
    https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html#children-of-a-coroutine
 */


private fun checkCoroutineChild() =
    /**
        Quando uma coroutine e lancada no escopo de outra, essa segunda é chamada de filha e
        heda o contexto da coroutine mais externa através
        * @see CoroutineScope.coroutineContext

        e o Job da nova coroutine tonar-se filho do Job da coroutine mais externa

        Esse relacionamento parent-child pode ser sobrescrito de 2 formas

        1 - Quando um escopo diferente é explicitamente especificado ao lancar a coroutine mais interna
            Exemplo: Usando GlobalScope.Launch, assim o Job da coroutine lancada por launch nao tem vinculo
        com a coroutine de fora
        2 - Quando uma instancia de Job diferente e passado como contexto para nova coroutine através de uma
            funcao builder, launch {} por exemplo
     */
    runBlocking {

        val task = launch {
            // Aqui o Escopo/Contexto foi definido explicitamente
            launch(Job()) {
                println("Start Job 1: Essa coroutine roda em seu proprio Job executando independentemente${Thread.currentThread().name}")
                delay(1000)
                println("End Job 1: Ela nao pode ser cancelada através do cancelamento de sua coroutine pai${Thread.currentThread().name}")
            }

            // essa coroutine herda o contexto
            launch {
                // minimo é uma diferenca de 6ms (500ms - 494ms) entre um delay e outro para funcionar
                // senao o processor é tao rapido que talvez nao faça diferença e essa coroutine e encerrada primeiro
                delay(494)
                println("Start Job 2: ${Thread.currentThread().name}")
                delay(1000)
                println("End Job 2: ${Thread.currentThread().name}")
            }
        }

        delay(500)
        // cancela a task e cancela as coroutines filhas que nao definem um escopo explicito diferente da sua coroutine pai
        task.cancel()
        delay(500)
        println("Ok")
    }


fun main() {
    checkCoroutineChild()
}