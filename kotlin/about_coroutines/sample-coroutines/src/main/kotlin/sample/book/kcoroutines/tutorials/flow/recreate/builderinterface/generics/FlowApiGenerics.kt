package sample.book.kcoroutines.tutorials.flow.recreate.builderinterface.generics

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.experimental.ExperimentalTypeInference


fun interface FlowApiCollector<T> {
    fun commit(value: T)
}

interface FlowApiProxy<T> {
    fun collect(collector: FlowApiCollector<T>)
}

/**
 * @see BuilderInference
 *
 * doc: Permite inferir tipos genericos de argumentos de funcao.
 * Quando usado em argumentos genericos de funcoes permite
 * inferir o tipo do argumento da "corpo|body" da funcao lambda
 *
 * A funcao sequence e a funcao flow sao exemplos que usam essa anotacao, o que facilita
 * a escrita da funcao, permitindo que o programador nao precise escrever o tipo generico
 * de retorno da funcao
 * @see sequence
 * @see flow
 */

@OptIn(ExperimentalTypeInference::class)
fun <T> buildFlowApiProxy(@BuilderInference exec: FlowApiCollector<T>.() -> Unit) : FlowApiProxy<T> = object : FlowApiProxy<T> {
    override fun collect(collector: FlowApiCollector<T>) {
        collector.exec()
    }
}


private fun checkFlowProxy() {
    val fn = buildFlowApiProxy {
        commit(2)
        commit(3)
        commit("S")
        commit(1.0)
    }
    fn.collect { println(it) }
}

private fun flowApi() {
    runBlocking {
        val s = flow {
            emit(1)
        }
        s.collect {
            println(it)
        }
    }
}

fun main() {
    checkFlowProxy()
}