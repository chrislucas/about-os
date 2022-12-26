package sample.ktutorial.ktacademy.tutorial.mutability

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

/*
    https://kt.academy/article/ek-mutability
    - Programas em kotlin podem ser projetados em modulos, esses sao
    compostos por objectos, funcoes, type aliases, e top-level properties.

    - Alguns desses elementos podem manter valores/"estado", por exemplo
    uma classe que contem uma propriedade mutavel (var) de leitura e escrita
    ou por objetos mutaveis (mutableList, mutableMap ...)
 */

class Data {
    var readAndWriteInt = 0
        private set

    fun editValue(value: Int) {
        readAndWriteInt += value
    }

    // withdraw = retirar, remover
    @Throws(Exception::class)
    fun withdraw(value: Int) {
        if (readAndWriteInt < value) {
            throw Exception("Error")
        }
        readAndWriteInt -= value
    }
}

private fun checkBehaviorMutableData() {
    /*
        Uma classe que mantem um estado pode ser util mais uma dor de cabeca.
        - Podemos representar elementos que mudando mediante a uma ação ou durante
        um intervalo de tempo, mas:
            - Pode se tornar dificil analisar bugs quando o estado de uma instancia dessa classe
            e modificado em varios pontos da aplicacao

            - A analise torna-se complicada quando precisamos olhar para multiplos pontos e
              o erro é algo muito inesperado

           - Um estado mutavel requer sincronizacao em solucoes multithreads, toda mudança é
           um potencial conflito

           - Elementos mutaveis requerem testes sob todos os valores/estdos que eles podem
           alcançar.

           - Quando um estado de uma classe muda e essa é relacionada com outras, essas precisam
           ser notificadas

     */
    val data = Data()
    println(data.readAndWriteInt)
    println(data.editValue(100))
    println(data.readAndWriteInt)
}

private fun checkThreadAndMutability() {
    var num = 0
    for (i in 1 .. 1000) {
        thread {
            Thread.sleep(10)
            num += 1
        }
    }

    //Thread.sleep(5000)
    println(num)
}

private fun checkSyncThreadAndMutability() {
    var num = 0
    val lock = Any()
    for (i in 1 .. 1000) {
        thread {
            Thread.sleep(10)
            synchronized(lock) {
                num += 1
            }
        }
    }
    //Thread.sleep(1000)
    println(num)
}

private suspend fun checkCoroutineAndMutability() {
    var num = 0
    coroutineScope {
        for (i in 1 .. 1000) {
            launch {
                delay(10)
                num += 1
            }
        }
    }
    println(num)
}

private fun checkAsyncProgrammingAndMutability() {
    repeat(5) {
        // resultados diferentes
        checkThreadAndMutability()
    }
    println("********************************************")
    repeat(5) {
        // aqui todos os resultados serao iguais por conta do runBlocking
        runBlocking {
            checkCoroutineAndMutability()
        }
    }
    println("********************************************")
    repeat(5) {
        checkSyncThreadAndMutability()
    }
}

/*
    mudar para 'main' para observar que as N vezes que
    a funcao checkCoroutineAndMutability for chamada ela
    dara respostas diferentes
 */

suspend fun test() {
    // resultados diferentes
    repeat(5) {
        checkCoroutineAndMutability()
    }
}


fun main() {
    checkAsyncProgrammingAndMutability()
}

