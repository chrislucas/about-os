package sample.book.kcoroutines.tutorials.flow.recreate


// function type
val flow: ((String) -> Unit) -> Unit = { emit ->
    emit("flow(ABC)")
}

private fun checkFlow() {
    flow { println(it) }
}

fun main() {
    checkFlow()
}