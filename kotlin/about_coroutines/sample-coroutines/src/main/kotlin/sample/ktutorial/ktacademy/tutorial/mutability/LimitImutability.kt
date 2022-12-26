package sample.ktutorial.ktacademy.tutorial.mutability

import java.util.*
import kotlin.collections.ArrayList


// https://kt.academy/article/ek-mutability
class CustomGettter(val id: String, val name: String, var email: String) {
    val username: String
        get() = "$name:$email"
}


private fun checkImmutability() {
    val userA = CustomGettter("1", "chris", "chris@test.com")

    println(userA.username)
    userA.email = "chrisjames@test.com"
    println(userA.username)
}


private fun calculate(): Int {
    print("calculating... ")
    return 42
}


val a = calculate()
val b get() = calculate()
val c: Int
    get() {
        calculate()
        return 34
    }

private fun checkCustomGetter() {
    println(a)
    println(a)
    println(b)
    println(b)
    println(c)
    println(c)
}


private fun checkDontDoThis() {
    val values = listOf(1, 2, 3)

    // cria um ArrayList kotlin
    // values.toMutableList()


    /*
        Downcasting que acabara numa exception
         Exception in thread "main" java.lang.UnsupportedOperationException
     */
    if (values is MutableList<Int>) {
        /**
        O resultado da funcao listOf e especifico da plataforma, por exemplo
        numa JVM, listOf Retornar uma instancia de Arrays.ArrayList
         * @see Arrays.ArrayList
         * que implementa herda de
         * @see AbstractList
         * que implementa a interface
         * @see List
            a interface List do Java. A interface List do java tem o metodo add, existindo
            um metodo com o mesmo nome na interface MutableList do Kotlin.

            Entretanto Arrays.ArrayList  não sobreescreve o metodo add que em
            AbstractList lança uma exceção por padrão e assim o codigo abaixo
             acaba lançando uma excecao

         */
        (values as MutableList<Int>).add(4)
    }

    println(values)
}


fun main() {
    //checkCustomGetter()
    checkDontDoThis()
}