package sample.ktutorial.docs.ctxanddispatchers

import kotlinx.coroutines.*
import java.util.concurrent.Executors

/*
    https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html#unconfined-vs-confined-dispatcher

    O dispatcher Unconfined inicia uma coroutine na thread que dispara a coroutine, mas somente até o primeiro
    ponto de suspensao.

    Após a suspensào, o dispatcher devolve a coroutine para a Thread que foi determinada pela suspend function
    que invocou o dispatcher

    Unconfined dispatcher é apropriado para coroutnies que nem consomem muita tempo da CPU nem atualizam uma
    estrutura de dados compartilhada  confinada a uma thread especifica (como UI e a MainThread do Android)
 */


val e = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

private fun check() = runBlocking {

    launch(Dispatchers.Unconfined) {
        println("Unconfined Context Inside runBlock without parameter: Thread ${Thread.currentThread().name}")
        println("********************************************************")
    }

    println("RunBlocking without Parameter: Thread ${Thread.currentThread().name}")
    println("********************************************************")


    runBlocking(Dispatchers.IO) {
        launch(Dispatchers.Unconfined) {
            println("Unconfined Context inside runBlock with IO Dispatcher: Thread ${Thread.currentThread().name}")
        }
        println("RunBlock with IO Dispatcher: Thread ${Thread.currentThread().name}")
        println("********************************************************")
    }


    runBlocking(Dispatchers.Default) {
        launch(Dispatchers.Unconfined) {
            println("Unconfined Context inside runBlock with Default Dispatcher: Thread ${Thread.currentThread().name}")
        }
        println("RunBlock with IO Dispatcher: Thread ${Thread.currentThread().name}")
        println("********************************************************")
    }

    runBlocking(e) {
        launch(Dispatchers.Unconfined) {
            println("Unconfined Context inside runBlock with NewSingleThreadExecutor: Thread ${Thread.currentThread().name}")
        }
        println("RunBlock with NewSingleThreadExecutor: Thread ${Thread.currentThread().name}")
        println("********************************************************")
    }

    e.close()

}


private fun checkDispatcher() = runBlocking {
    /*
        No exemplo abaixo vamo notar que o nome da thread antes do delay sera diferente
        após ele. Como descrito na documentação, O Dispatcher Unconfined inicia a coroutine
        na Thread dele e depois entrega a coroutine para a Thread da suspend function.
     */
    val outerScope = this
    launch(Dispatchers.Unconfined) {
        println("Unconfined Context: [Outer Scope: $outerScope, Inner Scope: $this]")
        println("Unconfined Context inside runBlock - Before: Thread ${Thread.currentThread().name}")
        delay(500)
        println("Unconfined Context inside runBlock - After: Thread ${Thread.currentThread().name}")

    }

    /*
        O Dispatcher herda do escopo exterior a ele por padrao. O dispatcher padrao para a coroutine
        lancada por runBlocking em particular e denominada CONFINED. Assim, ao herdar temos on efeito
        de confinar/limitar a execucao da coroutine a Thread que fioi criada pelo metodo runBlocking que
        por exemplo tem o comportamento PREDICTABLE FIFO SCHEDULING.

        O resultado ao executar essa funcao será que, a funcao launch com contexto Unconfiined
        terá sua execução iniciada na MainThread pois seu contexto herda do contexto de runBlocking
        porem terminar na Thread de execuçao default da funcao delay
     */

    launch {
        println("Main Context: [Outer Scope: $outerScope, Inner Scope: $this]")
        println("Main Context inside runBlock - Before: Thread ${Thread.currentThread().name}")
        delay(200)  // 1000
        println("Main Context inside runBlock - After: Thread ${Thread.currentThread().name}")
    }
}

private fun execute(fn: CoroutineScope.() -> Unit) = runBlocking { fn() }

private fun checkUnconfinedAndConfinedDispatchers() {
    /*
        Testando o comportamento do Dispatchers.Unconfined de entregar a coroutine a outra thread
     */
    execute {
        val outerScope = this

        /*
            Aqui temos um exemplo de como uma coroutine criada no contexto Unconfiened inicia numa thread
            e quando encontra um função suspensa (suspension point), entrega a coroutine criada para a thread dessa
            nova função suspensa.
         */
        launch(Dispatchers.Unconfined) {
            println("Unconfined Context: Outer Scope: $outerScope, Inner Scope: $this")
            // aqui a coroutine sendo executada numa thread
            println("Unconfined Context inside runBlock - Before Fake Operation | CurrentThreadName: ${Thread.currentThread().name}")

            val rs = async(Dispatchers.Default) { fakeOperationWithStringConcat() }

            withContext(Dispatchers.Default) {
                println("Size of string result: ${rs.await().length}")
            }

            // apos a operacao asincrona a coroutine e entregue a thread determinada pela funcao de suspencao
            println("Unconfined Context inside runBlock - After Fake Operation | CurrentThreadName: ${Thread.currentThread().name}")
        }

        println("****************************************************************************************************")


        launch (Dispatchers.IO) {
            println("IO Context: Outer Scope: $outerScope, Inner Scope: $this")
            println("IO Context inside runBlock - Before Delay operation | CurrentThreadName: ${Thread.currentThread().name}")
            delay(500)
            println("IO Context inside runBlock - After Delay operation | CurrentThreadName: ${Thread.currentThread().name}")
        }

        println("****************************************************************************************************")


        launch (Dispatchers.Unconfined) {
            println("Uncofined Context inside runBlock - Before Delay operation | CurrentThreadName: ${Thread.currentThread().name}")
            delay(15000)
            println("Uncofined Context inside runBlock - After Delay Operation | CurrentThreadName: ${Thread.currentThread().name}")
        }
    }
}


private fun checkCurrentThreadIODispatcher() {
    execute {
        val outerScope = this
        launch(Dispatchers.IO) {
            println("IO Context: Outer Scope: $outerScope, Inner Scope: $this")
            println("IO Context: inside runBlock - Before Fake Operation | CurrentThreadName: ${Thread.currentThread().name}")

            val rs = async(Dispatchers.Default) { fakeOperationWithStringConcat() }
            withContext(Dispatchers.Default) {
                println("Size of string result: ${rs.await().length}")
            }

            println("IO Context: inside runBlock - After Fake Operation | CurrentThreadName: ${Thread.currentThread().name}")
        }
    }
}


private fun checkCurrentThreadDefaultDispatcher() {

    /*
        A coroutine que herda seu contexto de runblocking {} executa todas as suas instrucoes
        na mesma thread que foi iniciada, quando uma coroutine que foi iniciada no Contexto Unconfiined
        encontra um suspension point, após a execuçao dele a coroutine em questao eh entregue a thread
        da suspensding function que fora executada
     */
    execute {
        val outerScope = this
        launch {
            println("EmptyCoroutineContext: Outer Scope: $outerScope, Inner Scope: $this")
            println("EmptyCoroutineContext: inside runBlock - Before | CurrentThreadName: ${Thread.currentThread().name}")

            val rs = async(Dispatchers.Default) { fakeOperationWithStringConcat() }
            withContext(Dispatchers.Default) {
                println("Size of string result: ${rs.await().length}")
            }

            println("EmptyCoroutineContext: inside runBlock - After | CurrentThreadName: ${Thread.currentThread().name}")
        }
    }
}



fun main() {
    checkCurrentThreadDefaultDispatcher()
}