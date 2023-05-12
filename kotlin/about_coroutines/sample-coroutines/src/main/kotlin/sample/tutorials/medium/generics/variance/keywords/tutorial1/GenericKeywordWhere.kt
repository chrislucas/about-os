package sample.tutorials.medium.generics.variance.keywords.tutorial1

/**
 * https://medium.com/@manuchekhrdev/kotlin-generics-in-out-where-9181ddbef01f
 * - a keyword WHERE é utilizada para adicionar RESTRICOES nos tipos usados como
 * parametros genericos
 */

private infix fun <T> T.charSequenceLessThan(other: T): Boolean
        where T : CharSequence, T : Comparable<T> = this < other

private infix fun <T> T.charSequenceLessThan(other: T): Boolean
        where T : Comparable<T> = this < other


private fun checkCompare() {
    println("ana" charSequenceLessThan "aaron")
    println(1 charSequenceLessThan 2)
    println(1.23 charSequenceLessThan 1.24)

}

fun main() {
    checkCompare()
}