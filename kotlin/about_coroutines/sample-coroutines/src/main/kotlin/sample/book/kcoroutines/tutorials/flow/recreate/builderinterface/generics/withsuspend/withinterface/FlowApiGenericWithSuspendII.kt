package sample.book.kcoroutines.tutorials.flow.recreate.builderinterface.generics.withsuspend.withinterface

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking


fun interface FlowApiCollector<T> {
    suspend fun commit(value: T)
}

interface FlowApi<T> {
    suspend fun collect(collector: FlowApiCollector<T>)
}

// sem o @BuilderInference
fun <T> createFlowApi(exec: suspend FlowApiCollector<T>.() -> Unit) = object : FlowApi<T> {
    override suspend fun collect(collector: FlowApiCollector<T>) {
        collector.exec()
    }
}

private fun checkCreateFlowApi() {
    val flowApi : FlowApi<Comparable<*>> = createFlowApi {
        commit(1)
        commit(2)
        commit(3.0)
        commit("S")
    }

    runBlocking {
        flowApi.collect(::println)
    }
}

private fun checkOriginalFlowApi() {
    val runner = flow {
        emit(1)
        emit("s")
    }

    runBlocking {
        runner.collect(::println)
    }
}

fun main() {
    checkCreateFlowApi()
}