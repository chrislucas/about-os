package sample.book.kcoroutines.tutorials.flow.recreate.builderinterface.generics.withsuspend.withinterface

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
    val flowApi = createFlowApi<Comparable<*>> {
        commit(1)
        commit(2)
        commit(3.0)
        commit("S")
    }

    runBlocking {
        flowApi.collect(::println)
    }
}


fun main() {
    checkCreateFlowApi()
}