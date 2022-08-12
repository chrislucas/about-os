package sample.ktutorial.docs.flow.tutorial.sites.minorks.terminaloperators

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.runBlocking

/**
 * https://blog.mindorks.com/terminal-operators-in-kotlin-flow
 *
 * - Terminal operators sao operadsores que iniciam o flow conectando o flow builder, os operadores
 * com o coletor
 */

suspend fun collectOperator() {
    (1..10).asFlow()
        .filter { it and 1 == 1 }
        // se parassemos por aqui o flow nao seria iniciado
        .map { it * it }
        // aqui, collect é um terminal operator
        .collect {
            println(it)
        }
}

/**
 * Splica um funcao em cada valor emitido pelo flow e emite um valor unico
 */
suspend fun reduceOperator() {
    val result = "christoffer luccas"
        .split("")
        .asFlow()
        .reduce { acc, value ->
            if (value.matches("[aeiou\\s]".toRegex())) {
                acc + value
            } else {
                acc
            }
        }
    println(result)
}

fun main() {
    runBlocking {
        //collectOperator()
        reduceOperator()
    }
}