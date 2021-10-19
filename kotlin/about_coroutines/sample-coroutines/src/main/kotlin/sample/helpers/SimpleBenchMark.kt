package sample.helpers

fun calculate(fn: () -> Unit): Double {
    val s = System.currentTimeMillis()
    fn()
    return (System.currentTimeMillis() - s ) * 1.0 / 1000.0
}