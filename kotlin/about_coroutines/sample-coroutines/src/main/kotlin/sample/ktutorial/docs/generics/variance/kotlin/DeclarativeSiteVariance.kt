package sample.ktutorial.docs.generics.variance.kotlin

import sample.generics.variance.StaticModels.TemplateProducer

/**
 *
 *
 *
 * https://kotlinlang.org/docs/generics.html#declaration-site-variance
 * @see sample.generics.variance.StaticModels
 * @see sample.generics.variance.AboutWildCard
 * @see sample.generics.variance.VarianceCovarianceAndContraVariance
 *
 * Relembrando sobre Objetos Producers e COnsumers
 *  - o minemonico PECS - Producer Extends, Consumer Super
 *  - Num PRODUCER List<? extends T> nao vamos conseguir chamar metodos add e set
 *  -
 */


private fun checkProducer() {

    // Um objeto producer nao nos da acesso aos metodos ADD e SET
    // mas temos acesso ao clean, o que torna o objeto mutavel

    val readOnlyInt = TemplateProducer { mutableListOf(1, 2, 3) }
    println(readOnlyInt)

    //producer.readOnlyValues.add(4 as Nothing)
    //println(producer.readOnlyValues)

    val readOnlyString = TemplateProducer { mutableListOf("I", "'m", "robKotic") }
    println(readOnlyString)

    readOnlyString.readOnlyValues.clear()
    println(readOnlyString)

    val readOnlyValues = TemplateProducer { listOf(3, 4, 5, 6) }
    // Erro: essa operacao nao vai ser suportada por o list do kotlin nao possui essa operacao
    readOnlyValues.readOnlyValues.clear()
    println(readOnlyValues)
}

fun main() {
    checkProducer()
}