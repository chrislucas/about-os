package sample.ktutorial.docs.generics.variance.declarativesitevariance.covariance

import sample.generics.variance.StaticModels.FlexibleTextField
import sample.generics.variance.StaticModels.TextField

/*
    Estudos e testes baseados no link
    https://kotlinlang.org/docs/generics.html#declaration-site-variance
 */

class WrapperCollection<T> {

    val values: MutableList<T> = mutableListOf()

    operator fun <A : T> plusAssign(data: A) {
        values += data
    }

    override fun toString(): String {
        return values.joinToString(", ")
    }
}

data class WrapperData<T>(val data: T)

/*
    Como sabemos
    Seja a classe Box<T>
    Box<Object> a;
    Box<String> b;
    em java 'b' nao é um subtipo de 'a' logo nao podemos fazer a = b
    Para podermos fazer isso usamos o wildcard <? extends T>

    Em kotlin temos uma forma de explicar que queremos isso para o compilador.
    Chamado de DECLARATION-SITE VARIANCE, podemos MARCAR/ANOTAR o tipo T para garantir
    que a classe parametrizada com T so retorne/produza T ou subtipos do mesmo
    e nunca consuma

 */

private fun notASubtype() {
    var a: WrapperData<Any>
    val b: WrapperData<String> = WrapperData("eu sou b")
    //a = b
    //println(a)

    val a1: WrapperData<out Any> = WrapperData("eu sou b1") // Wrapper<String>
    println(a1)
}

internal interface Producer<out T> {
    fun get(): T
}

internal class ProducerImpl<T>(private val template: () -> T) : Producer<T> {
    override fun get(): T = template()
}

private fun checkProducer() {
    val producer: Producer<TextField> = ProducerImpl {
        FlexibleTextField(
            "#1", "fleixble textfield 1", "fleixble textfield 1"
        )
    }

    println(producer.get())

    val producerStr: Producer<Any> = ProducerImpl { "text" }
    println(producerStr.get())
}

private fun checkProducer(producerStr: Producer<TextField>) {
    val producerAny: Producer<Any> = producerStr
    println(producerAny.get())
}


private fun check() {
    val wrapperTextField = WrapperCollection<TextField>()
    wrapperTextField += FlexibleTextField("#1", "", "")
    wrapperTextField += FlexibleTextField("#2", "", "")
    println(wrapperTextField)
}


fun main() {
    //checkProducer()
    //checkProducer(ProducerImpl { FlexibleTextField("#3", "", "") })
}