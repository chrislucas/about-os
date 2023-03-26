package sample.ktutorial.docs.generics.variance.declarativesitevariance.covariance


import sample.generics.variance.StaticModels.*


/*
    https://kotlinlang.org/docs/generics.html#declaration-site-variance

     interessante: Nao é possivel criar um mutableList<out T> por


 */
class ReadOnlyCollection<out T>(val producer: () -> List<T>) {

    private val values: List<T> = producer()

    fun get(): List<T> = values
}


private fun <T> check(values: MutableList<out T>) {
    val cp: List<T> = values
}


private fun check() {
    val readOnly: ReadOnlyCollection<TextField> = ReadOnlyCollection {
        listOf(FlexibleTextField("", "", ""))
    }

    println(readOnly.get())
}