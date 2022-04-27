package sample.functional.sam.intterface


fun interface Evaluantion<V> {
    fun check(value: V): Boolean
}

private fun checkEvaluation() {
    val isOdd = Evaluantion<Int> { it and 1 == 1 }
    println(isOdd.check(1))
}

fun interface AnotherEval<V> {
    fun check(value: V, fn: (V) -> Boolean): Boolean
}

private fun checkAnotherEvaluation() {
    val eval = AnotherEval<Int> { v, fn -> fn(v) }
    println(eval.check(2) { it % 2 == 0 })
}

fun main() {
    checkAnotherEvaluation()
}