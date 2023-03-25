package sample.ktutorial.docs.generics.variance.declarativesitevariance

import sample.generics.variance.StaticModels.FlexibleTextField
import sample.generics.variance.StaticModels.TextField

/*
    Estudos e testes baseados no link
    https://kotlinlang.org/docs/generics.html#declaration-site-variance
 */

class WrapperData<T> {

    val values: MutableList<T> = mutableListOf()

    operator fun <A : T> plusAssign(data: A) {
        values += data
    }

    override fun toString(): String {
        return values.joinToString(", ")
    }
}


interface Box<out T> {
    fun next(): T
}

private fun check() {
    val wrapperTextField = WrapperData<TextField>()
    wrapperTextField += FlexibleTextField("#1", "", "")
    wrapperTextField += FlexibleTextField("#2", "", "")
    println(wrapperTextField)
}


fun main() {
    check()
}