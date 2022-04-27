package sample.book.kcoroutines.tutorials.flow.recreate.withreceiver


/**
 * https://kt.academy/article/how-flow-works
 */
fun interface FlowCollector {
    fun emit(value: String)
}

private fun createExtensionFunctionForAFunctionalInterface() {
    val f: FlowCollector.() -> Unit = {
        emit("ABC")
    }
    f { str -> println(str) }
}

fun main() {
    createExtensionFunctionForAFunctionalInterface()
}