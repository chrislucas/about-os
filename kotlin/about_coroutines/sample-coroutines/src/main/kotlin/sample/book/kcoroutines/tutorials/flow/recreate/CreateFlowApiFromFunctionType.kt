package sample.book.kcoroutines.tutorials.flow.recreate


// function type
private val simulateFlowApi: ((String) -> Unit) -> Unit = { emit ->
    emit("flow(ABC)")
}

private fun checkFlow() {
    simulateFlowApi { println(it) }
}

fun main() {
    checkFlow()
}