package sample.book.kcoroutines.tutorials.flow.recreate.builderinterface.generics.withsuspend.functional

/**
 * A versao final que parece com a implementacao original da api de Flow, com generics
 * e suspend function
 * https://kt.academy/article/how-flow-works
 */

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.experimental.ExperimentalTypeInference

fun interface FlowApiCollector<T> {
    suspend fun commit(value: T)
}

fun interface FlowApiProxy<T> {
    suspend fun collect(collector: FlowApiCollector<T>)
}

@OptIn(ExperimentalTypeInference::class)
fun <T> createFlowApiProxy(@BuilderInference exec: suspend FlowApiCollector<T>.() -> Unit) =
    FlowApiProxy<T> { collector ->
        collector.exec()
    }

private fun checkCreateFlowApiProxy() {
    val flowApiProxy = createFlowApiProxy {
        commit(1)
        commit(1.0)
        commit("lks")
    }
    runBlocking {
        flowApiProxy.collect(::println)
    }
}

private fun checkOriginalFlowApi() {
    val runner = flow {
        emit(1)
        emit("Lks")
        emit(1.0)
    }
    runBlocking {
        runner.collect(::println)
    }
}


fun main() {
    checkCreateFlowApiProxy()
    checkOriginalFlowApi()
}