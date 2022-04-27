package sample.book.kcoroutines.tutorials.flow.recreate.builderinterface


/**
 * https://kt.academy/article/how-flow-works
 *
 * Ao inves de criar uma extension function a partir da Functional Interface FlowCollector
 * e chamada direto atraves de uma lambda, podemos delegar a chamada para uma outra interface
 * chama-la atraves de uma implementacao anonima
 *
 */
fun interface FlowCollector {
    fun emit(value: String)
}

// Recebe/Coleta um FlowCollector para executa-lo
interface FlowProxy {
    fun collect(collector: FlowCollector)
}

fun flowApi(execute: FlowCollector.() -> Unit) = object : FlowProxy {
    override fun collect(collector: FlowCollector) {
        collector.execute()
    }
}

private fun checkFlow() {
    val f = flowApi {
        emit("1")
        emit("2")
        emit("3")
    }
    f.collect { println(it) }
}

private fun check() {
    val execute: FlowCollector.() -> Unit = {
        emit("ABC")
    }

    val flowCollect: FlowProxy = object : FlowProxy {
        override fun collect(collector: FlowCollector) {
            collector.execute()
        }
    }

    execute {
        println("Chamando a ext function direto: $it")
    }

    flowCollect.collect {
        println(it)
    }
}

fun main() {
    checkFlow()
}