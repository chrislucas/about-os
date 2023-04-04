package sample.tutorials.medium.generics.variance


/**
 * @see ReadOnly
 * @see
    https://proandroiddev.com/understanding-type-variance-in-kotlin-d12ad566241b
 */
interface WriteOnly<in T> {
    fun write(value:  T)
}