package sample.tutorials.stackoverflow.generics.declarativesites

/*
    https://stackoverflow.com/questions/64835413/use-site-vs-declaration-site-difference-in-type-projections-in-kotlin
 */

class Consumer<in T> {

    private val elements: MutableList<T> = mutableListOf()

    operator fun plusAssign(value: T) {
        elements += value
    }

    // fun get() = elements.last()
}

class Producer<out T> (request: () -> MutableList<T>){

    private val elements: MutableList<T> = request()

    operator fun <T> plusAssign(value: T) {
        // elements.add(value)
        // elements.add(value)
    }

    fun get() = elements.last()
}


fun main() {

}