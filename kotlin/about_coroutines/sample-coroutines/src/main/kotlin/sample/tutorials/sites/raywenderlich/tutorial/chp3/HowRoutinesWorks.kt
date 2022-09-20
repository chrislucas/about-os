package sample.tutorials.sites.raywenderlich.tutorial.chp3

import kotlinx.coroutines.*
import sample.ktutorial.logCoroutineScope
import kotlin.coroutines.CoroutineContext

/*
    https://www.raywenderlich.com/books/kotlin-coroutines-by-tutorials/v2.0/chapters/3-getting-started-with-coroutines#toc-chapter-007-anchor-005

    - Pontos interessantes para recordarmos
        - quando iniciamos um programa, geralmente através de uma funcao denominada main, o S.O inicia
        uma ROTINA
        - Quando a funcao main chama outra funcao, inicia-se uma SUBROTINA atrelada a ROTINA que a iniciou
            - uma SUBROTINA eh uma ROTINA
        - Essas rotinas sao adicionadas a uma estrutura de pilha e ao final da execucao de cada uma elas sao
        desempilhadas.

        - Executar uma subrotina eh realizar uma chamada denominada de "BLOQUEANTE" - Blocking Call
            - Uma coroutine é definida com uma subrotina que é executada como
            uma chamada NAO BLOQUEANTE - Non-blocking call

        - Dessa forma a principal diferenca de uma subrotina e uma corrotina ou coroutine
           eh q a ultima pode executar em paralelo com outros codigos, inclusive o codigo
           que a invocou
 */


private suspend fun checkSuspendFunction() {

    suspend fun funA() =
        CoroutineScope(Dispatchers.IO).async {
            println("Execute A")
            delay(5000)
        }


    suspend fun funB() =
        CoroutineScope(Dispatchers.IO).async {
            println("Execute B")
            delay(1000)
        }


    suspend fun funC(ctx: CoroutineContext) {
        repeat(10001) {
            CoroutineScope(ctx).launch {
                logCoroutineScope("$it-esimo")
            }
        }
    }

    /*
        https://www.raywenderlich.com/books/kotlin-coroutines-by-tutorials/v2.0/chapters/3-getting-started-with-coroutines#toc-chapter-007-anchor-005
        Pontos importantes sobre o trecho de codigo que lanca uma coroutine

        1 - launch eh uma coroutine builder, existem mais funcoes que constroem coroutines

        2 - Quando lança-se uma coroutine precisamos prover uma CorotuineScope porque a coroutine eh executada
        por um mecanismo em background que nao está a par do ciclo de vida do ponto que iniciou a coroutine.
            - Eh preciso uma preocupacao caso o programa que iniciou a coroutine encerre sua execucao antes da coroutine
            terminar a sua execucao

            - Usar o GlobalScope marca explicitamente que a coroutine lançada por esse escopo está finculada ao
            ciclo de vida da aplica (Escopo Global)

     */
    CoroutineScope(Dispatchers.IO).launch {
        funC(coroutineContext)

        funB().await()
        funC(Dispatchers.Default)

        funA().await()
        funC(Dispatchers.Unconfined)
    }.join()

}


private fun runGlobalScope() {
    repeat(10001) {
        /*
            Vincular uma coroutine ao escopo global vincula a mesma ao ciclo de vida da aplicacao

         */
        GlobalScope.launch {
            logCoroutineScope("$it-esimo")
        }.invokeOnCompletion {
            it?.let { println("Complementando com a excecao $it") } ?: println("completado")
        }
    }
    // Por usar o escopo global para lancar a coroutine precisamos colocar um delay para que a funcao
    // tenha tempo de ser executada antes da funcao main terminar sua execucao
    Thread.sleep(1000)
}

fun runCheck() = runBlocking {
    checkSuspendFunction()
}

/*
     CoroutineScope.launch(
            context: CoroutineContext = EmptyCoroutineContext,
            start: CoroutineStart = CoroutineStart.DEFAULT,
            block: suspend CoroutineScope.() -> Unit
        )

        CoroutineContext é uma estrutura de dados persistente que armazena informacao
        sobre a coroutine que fora lançada
            - Ela pode conter objetos como: Job e Dispatcher
            - a funcao builder launch usa o EmptyCoroutineContext
                - Esse contexto aponta para o contexto especificado
                pela CoroutineScope
            - Podemos criar contextos personalizados

        CoroutineStart: Modos que podemos inicar uma coroutine
            - DEFAULT: Immediately schedules a coroutine for
                execution according to its context.
            - LAZY: Starts coroutine lazily.
            - ATOMIC: Same as DEFAULT but cannot be cancelled before it starts.
            - UNDISPATCHED: Runs the coroutine until its first suspension point.

        Por fim a funcao launch recebe como ultimo argumento uma funcao lambda.
        A funcao esperada como argumento é uma lamnda with receiver cujo receiver
        eh justamento uma CoroutineScope -> suspend CoroutineScope.() -> Unit.
        Isso permite agregar Jobs como lancar uma coroutine a partir
        de outra coroutine
 */

fun main() {
    runGlobalScope()
}