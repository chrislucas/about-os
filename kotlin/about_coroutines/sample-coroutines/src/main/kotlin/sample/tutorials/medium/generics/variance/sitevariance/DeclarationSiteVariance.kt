package sample.tutorials.medium.generics.variance.sitevariance

import sample.generics.variance.StaticModels.FlexibleTextField
import sample.generics.variance.StaticModels.TextField
import sample.generics.variance.StaticModels.AutoCompleteFlexibleTextfield
import sample.generics.variance.StaticModels.BaseTextField

/**
    Declaration-site variance
    https://proandroiddev.com/understanding-type-variance-in-kotlin-d12ad566241b
    - Kotlin permite especificar o modificador de variance na definicao da interface ou classe
 * @see ReadOnly
 * @see WriteOnly
 *
 * Ao especificar na definicao da classe/interface o modificador de variancia, todas as metodos que
 * podem utilizar o tipo parametrizado como argumento ou retornar-o
     * class Clazz<in A, out R>
     *     fun invoke(value: T)
     *     fun process(value: T): R
     *

 *  - Isso diferencia o kotlin do Java onde usamos o wildcar em cada método que precisamos/queremos
 */

sealed class Animal

object Eagle : Animal()
object Bear: Animal()

private fun <T> useSiteVariance(provider: List<T>, consumer:  MutableList<in T>) {
    consumer.addAll(provider)
    println(consumer)
}


private fun checkUseSiteVariance() {
    val provider = listOf(
        FlexibleTextField("#1", "", ""),
        FlexibleTextField("#2", "", ""),
        FlexibleTextField("#3", "", "")
    )

    useSiteVariance(provider, mutableListOf<TextField>())
}

private fun <T> useSiteVarianceMutableList(provider: MutableList<T>, consumer:  MutableList<in T>) {
    consumer.addAll(provider)
    // provider.addAll(consumer)
    println(consumer)
}

private fun <T> add(consumer: MutableList<in T>, value: T) {
    consumer += value
}

interface InternalConsumer<in T> {
    fun consumer(value: T)
}

class HolderConsumer<T>(val consumer: InternalConsumer<T>)


private fun checkAdd() {
    val consumer = mutableListOf<TextField>()
    add(consumer, FlexibleTextField("#1", "", ""))
    add(consumer, AutoCompleteFlexibleTextfield("#1", "", ""))
    add(consumer, TextField("#3", ""))
    //add(consumer, BaseTextField("#1", ""))
    println(consumer)
    println("************************************************************")

    val consumer2 = mutableListOf<BaseTextField>()
    add(consumer2, BaseTextField("#1", ""))

}


fun main() {
    checkUseSiteVariance()
}