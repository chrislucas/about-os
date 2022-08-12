package sample.tutorials.sites.kotlinlang.flow


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking


/*
    https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow-collector/

    Usado como um intermediador ou um "coletor terminal" do flow e
    representa uma entidade que aceita valores emitidos pelo Flow

    Essa interface nao deve ser implementada direta, ao invés disso devemos usar um builder flow
    https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/flow.html

    quando implemena
 */


suspend fun checkFlowCollector() = flow {
    println(this)
    // emite um valor
    emit(0xff)
}.flowOn(Dispatchers.IO)

fun main() {
    runBlocking {
        checkFlowCollector().collect {
            // coleta  o valor emitido
            println(it)
        }
    }
}