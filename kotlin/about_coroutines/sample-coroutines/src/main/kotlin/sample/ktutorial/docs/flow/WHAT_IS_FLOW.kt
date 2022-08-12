package sample.ktutorial.docs.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

/**
 * https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/
 *
 */

/*
    Um data stream assincrono que sequencialmente emite valores,
    finalizando-se normalmente ou lançando uma exceção
 */
private suspend fun checkItOut() {
    val flow = flowOf(1, 2, 3, 4)
    try {
        flow.collect {
            println(it)
        }
    } catch (e: Exception) {
        println(e)
    }
}

/**
 * zip - operador intemediario
 * https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/zip.html
 * uni os valores de um flow p a um flow q = p.zip(q)
 * Usando uma funcao lambda que recebe os 2 valores. O resultado eh um novo
 * flow.
 *
 * A funcao zip se encerra assim que um dos 2 flows se encerra, sendo o outro
 * cancelado
 */
private suspend fun zipFlow() {
    val p = (1..30).asFlow()
        .onEach {
            delay(1000)
        }
    val q = "christoffer luccas".split("")
        .asFlow().onEach {
            delay(15)
        }

    p.zip(q) { fp, fq ->
        "$fp $fq"
    }.collect {
        println(it)
    }
}


fun main() {
    runBlocking {
        //checkItOut()
        zipFlow()
    }
}