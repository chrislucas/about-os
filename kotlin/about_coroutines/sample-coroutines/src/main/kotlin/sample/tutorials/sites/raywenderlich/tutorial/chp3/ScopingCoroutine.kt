package sample.tutorials.sites.raywenderlich.tutorial.chp3

import kotlinx.coroutines.*
import sample.ktutorial.logCoroutineScope
import kotlin.coroutines.CoroutineContext

/*
    https://www.raywenderlich.com/books/kotlin-coroutines-by-tutorials/v2.0/chapters/3-getting-started-with-coroutines

    - Apesar de as coroutinas serem lancadas em paralelo com a aplicacao principal, no princípio
    do funcionamento dessa api, o fim da funcao principal nao implicava no fim da execucao da coroutine

    - Isso gerava bugs como a continuacao da execucacao de coroutine mesmo que o programa
    principal tivesse sido interrompido

    - Para mitigar esse caso, foi desenvolvido a CoroutineScope.
        - Cada instancia de escopo sabe a qual contexto está relacionada
        - Cada escopo possui o seu ciclo de vida
            - Se o ciclo de vida de um determinado escopo chega ao fim, a coroutine
            que esta sendo executada nele chega ao fim tambem
 */


private suspend fun testLifeCycleScope() {
    val job = GlobalScope.launch {
        val job = CoroutineScope(CoroutineName("The coroutine")).launch {
            logCoroutineScope("Hello World")
        }

        with(job) {
            join()
            invokeOnCompletion {
                println("Ola, it's me completed")
            }
        }


        println(0xff)
        /*
            Se o ciclo de vida de um escope termina enquanto uma coroutine ainda está em
            execucao, a coroutine em questao para
         */
    }

    with(job) {
        join()
        invokeOnCompletion {
            println("Completed GlobalScope.launch")
        }
    }

    // Sem uma Thread.sleep logo aqui ou a execucao dessa coroutine
    // dentro de um runblocking, a funcao main nao vai esperar a coroutine termianr
    // Thread.sleep(1000)
}


private fun checkTestLifeCycleScope() {
    runBlocking { testLifeCycleScope() }
}

/*
    Existem 2 formas de usar a funcao launch

    1 - Atraves da funcao GlobalScope
    2 - Criando uma CoroutineScope
        2a -> Usando a funcao CoroutineScope
            public fun CoroutineScope(context: CoroutineContext): CoroutineScope
        2b -> Criando sua propria implementacao de CoroutineScope e provendo para
        ela uma instancia de CoroutineContext

    A primeira eh utilizada quando os resultados da coroutine nao sao utilizados, a segunda exatamente o oposto
    e ainda é util quando precisamos vincular os Jobs ao ciclo de vida de um certo objeto
        - Exemplo de uso no Android ao vincular a coroutine com o ciclo de vida de um Fragment ou Activity

    + Cancelar uma Coroutine

       Ha casos que mesmo atraves do ciclo de vida de uma coroutine ou um cancelamento manual nao necessariamente
       cancelam uma coroutine -
       Fonte https://www.raywenderlich.com/books/kotlin-coroutines-by-tutorials/v2.0/chapters/3-getting-started-with-coroutines

 */
class CustomCoroutineScope(override val coroutineContext: CoroutineContext) :
    CoroutineScope {
    override fun toString(): String =
        "CoroutineScope(coroutineContext=$coroutineContext)"
}



private suspend fun checkCustomCoroutineScope() {
    CustomCoroutineScope(Dispatchers.IO).launch {
        logCoroutineScope("Lancando uma coroutine de um escopo personalizado")
    }.run {
        join()
        invokeOnCompletion {
            println("Completed Custom Coroutine")
        }
    }
}

fun main() {
    checkTestLifeCycleScope()

    runBlocking {
        checkCustomCoroutineScope()
    }
}