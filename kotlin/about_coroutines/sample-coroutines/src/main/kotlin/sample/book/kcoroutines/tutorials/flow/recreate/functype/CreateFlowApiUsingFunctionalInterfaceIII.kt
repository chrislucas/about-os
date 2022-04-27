package sample.book.kcoroutines.tutorials.flow.recreate.functype

/**
 * https://kt.academy/article/how-flow-works
 */
fun interface FlowCollector {
    fun emit(value: String)
}

/**
 *
 */
private fun createLambdaFromFunctionalInterface() {
    val f: (FlowCollector) -> Unit = {
        it.emit("A B C")
    }

    f { println(it)}
}


fun main() {
    createLambdaFromFunctionalInterface()
}