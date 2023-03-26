package sample.ktutorial.docs.generics.variance.declarativesitevariance.invariance


/**
 * https://kotlinlang.org/docs/generics.html#declaration-site-variance
 * @see Comparable
 * public interface Comparable<in T>
 *
 * lower bound -
 *
 */
private fun cehck(comparableNumber: Comparable<Number>) {
    comparableNumber.compareTo(1.0)
    val comparableDouble: Comparable<Double> = comparableNumber
}


fun main() {

}