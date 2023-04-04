package sample.tutorials.medium.generics.variance

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import sample.generics.variance.StaticModels.AutoCompleteTextField
import sample.generics.variance.StaticModels.FlexibleTextField
import sample.generics.variance.StaticModels.TextField
import sample.tutorials.medium.generics.variance.Animal.*

/*
    https://proandroiddev.com/understanding-type-variance-in-kotlin-d12ad566241b

    Variance -
        "Relacao entre tipos genericos que compartilham a mesmo tipo-base mas com tipo de argumento diferente "
        - Exemplo List<Any>, o tipo base é List e o tipo do argumento Any
 */


fun <T> copyToSuper(
    source: MutableList<T>,
    dest: MutableList<in T>
): MutableList<in T> {
    source.forEach {
        dest += it
    }
    return dest
}


private fun <T> copyFromSubToSuper(
    source: MutableList<out T>,
    dest: MutableList<in T>
): MutableList<in T> {
    source.forEach {
        dest += it
    }
    return dest
}


sealed class Animal {
    object Cat : Animal() {
        override fun toString(): String = "CAT"
    }

    object Dog : Animal() {
        override fun toString(): String = "DOG"
    }
}


private fun checkCopy() {
    val animals = mutableListOf(Cat, Cat, Dog)
    val cats = mutableListOf(Cat)
    val dogs = mutableListOf(Dog)
    println(copyToSuper(cats, animals))
    println(copyToSuper(dogs, animals))
    // compiler error
    // copy(cats, dogs)
    // copy(dogs, cats)
}

private fun checkAnotherCopy() {
    val animals = mutableListOf(Cat, Cat, Dog)
    val cats = mutableListOf(Cat)
    val dogs = mutableListOf(Dog)
    println(copyFromSubToSuper(cats, animals))
    println(copyFromSubToSuper(dogs, animals))
}

/*
    Invariancia
    - Uma classe generica e chamada de invariante "Em seu tipo de parametro" (Class<T> por exemplo)
        quando para 2 diferntes tipos A e B, Class<A> nao é nem subtipo nem supertipo de Class<B>

        Como vemos no metodo abaixo, nao é seguro (e por isso o compilador impede) passar MutableList<Cat> para
        um metodo que recebe MutableList<Any>, mesmo Cat sendo um subtipo de Any. MutableList<Cat> nao é um subtipo
        de MutableList<Any> e o contrário tbm.
 */
private fun checkMutableListInvariance() {

    /*
        Enquanto é seguro passar Cat para um metodo que aceita Any, passar MutableList<Cat>
        para um metodo que aceita MutableList<Any> nao o é. Veja o que poderia ser feito
        no metodo abaixo
     */

    fun add(list: MutableList<Any>) {
        // uma lista de Any que aceita um Dog. Imagine se fosse possível passar uma lista de Cat
        // ao iterar pela lista buscando somente Cat teriamos um Dog no meio
        // Se MutableList nao fosse in
        //
        // variante no tipo de parametro, ao passar a lista de gatos
        // receberiamos na linha que executa um add um ClassCastEception
        list.add(Dog)
    }

    fun addAnimals(list: MutableList<Animal>) {
        list.add(Dog)
    }

    val cats = mutableListOf(Cat)

    // add aceita somente MutableList<Any> e MutableList<Animal> ou MutableList<Cat> nao é um subtipo
    // de MutableList<Any>
    // add(cats)

    // tambem nao funciona MutableList<Cat> nao é um subtipo de MutableList<Animal>
    // addAnimals(cats)

    /*
        Aqui, como a lista é imutavel, nao tem como adiconarmos nada nela e o que foi passado
        tem no minimo os mesmos comportamentos que Any
     */
    fun execute(values: List<Any>) {
        values.forEach(::println)
    }

    execute(cats)

    val animals = mutableListOf(Cat, Dog, Dog, Cat, Dog)

    execute(animals)
}


/**
    Covariance (Preserved subtyping relation)
        - Uma classe Generica e chamada de covariante "em seu tipo de parametro / tipo parametrizado" quando
        ocorre o seguinte: Class<A> e um subtipo de Class<B>
        - Por exemplo, em kotlin a interface List representa uma colecao somente de leitura. Se A é subtype
        de B entao List<A> ;e subtipo de List<B>

  ** @see ReadOnly
     * veja que a keyword out é o modificador de variancia usado para declarar classes como covariante para
     * certos tipos parametrizaveis
 */

private fun checkCovariance() {

    fun  producer(producerTextField: ReadOnly<TextField>) {
        runBlocking {
            val textField =  flow<TextField> {
                producerTextField.producer()
            }.collect()

            println(textField)
        }
    }

    producer(object : ReadOnly<FlexibleTextField> {
        override fun producer() = FlexibleTextField("", "", "")
    })


    val readOnlyTextField: ReadOnly<TextField> = object : ReadOnly<AutoCompleteTextField> {
        override fun producer(): AutoCompleteTextField =
            AutoCompleteTextField("#id", "first AutocompleteTxtFiedl")
    }

    println(readOnlyTextField)
}

/**
 *
 */

/*
    Types em Kotlin

    A -> A? = A é um subtipo de A que pode ser nulo
    A? nao é um subtipo de A, nao podemos atribuir um ao outro
        - var a: Cat? = null
        - val b: Cat = a // nao compila
 */

private fun checkTypesAndSubtype() {
    fun receiveNonNullableCal(cat: Cat) {
        println(cat)
    }

    val nullableCat: Cat? = null
    // nao compila
    // receiveNonNullableCal(nullableCat)

    // val a: Cat? = null
    // val b: Cat = a // nao compila

}

fun main() {
    // -checkAnotherCopy()
    checkCovariance()
}