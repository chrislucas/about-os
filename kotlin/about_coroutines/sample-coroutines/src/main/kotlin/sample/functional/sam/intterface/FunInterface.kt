package sample.functional.sam.intterface

/**
 * https://kotlinlang.org/docs/fun-interfaces.html
 *
 * Functional (SAM) interface: Uma inferface com um unico meodo abstrato.
 *
 * Uma functional interface pode ter multiplos membros "concretos" mas um
 * unico membro abstrato
 */


fun interface SingleAbstractMethod {
    fun exec()
}

/**
 * Conventions
 * com a functional interface nao precisamos mais implementar uma interface Anonima ou
 * criar uma classe que implmente-a, podemos usar expressoes lambdas
 */

val lambda = SingleAbstractMethod { println("SAM") }

interface SAM {
    fun invoke()
}

val anoun = object : SAM {
    override fun invoke() {
        println("SAM")
    }
}

val anoun2 = object : SingleAbstractMethod {
    override fun exec() {
        println("Anoun SAM 2")
    }
}

private fun checkFunctionalInterface() {
    lambda.exec()
    anoun.invoke()
}


fun main() {
    checkFunctionalInterface()
}