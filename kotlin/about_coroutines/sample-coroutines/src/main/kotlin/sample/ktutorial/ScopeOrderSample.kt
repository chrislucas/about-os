package sample.ktutorial

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private fun check() {

    /*
        https://betterprogramming.pub/how-to-fire-and-forget-kotlin-coroutines-in-spring-boot-40f8204aac86#
     */

    fun test(fn: suspend () -> Unit = {}) = runBlocking {
        coroutineScope {
            launch {
                fn()
                print(1)
            }
            print(2)
        }
        print("3\n")
    }

    fun checkTest() {
        test()
        test {
            delay(500)
        }
    }


    fun helloWorld(fn: suspend () -> Unit = {}) = runBlocking {

        launch {
            fn()
            print("world")
        }

        /*
            Ordem de execucao
            1 - coroutineScope
            2 - runBlocking
            3 - launch
         */

        coroutineScope {
            print(" scope[$this] ")
        }

        print("hello")
    }

    fun checkHelloWorld() {
        helloWorld()
        print("\n")
        helloWorld {
            delay(1000)
        }
    }

    checkHelloWorld()

}

/*
    Funcao abaixo testa a ordem que as funcoes builders de coroutine sao disparadas

    Leia
    https://stackoverflow.com/questions/74184077/kotlin-coroutines-runblocking-vs-coroutinescope

 */
private fun checkOrder2() {

    /*
        Order
        1 -coroutineScope
        2 - launch inside runBlocking
        3 - launch inside coroutineScope
        4 runblocking
     */
    fun check1() = runBlocking {

        launch {
            println("launch inside runBlocking fun scope[$this]")

        }

        coroutineScope {
            launch {
                println("launch inside coroutineScope fun scope[$this]")
            }
            println("coroutineScope scope[$this]")
        }

        println("runBlocking scope[$this]")
    }

    /*
        Orderm
        1 - runBlocking
        2 - launch
     */
    fun check2(fn: suspend () -> Unit = {}) = runBlocking {
        launch {
            println("Launch inside runblocking scope [$this]")
        }
        fn()
        println("runblocking scope[$this]")
    }


    // check2()
    // com delay a ordem muda
    // check2 { delay(10) }


    fun check3() = runBlocking {

        launch {
            println("Launch inside runblocking scope [$this]")
        }

        runBlocking {
            println("RunBlocking inside runblocking scope [$this]")

        }

        println("RunBlocking scope[$this]")
    }


    check3()
}


fun main() {
    checkOrder2()
}