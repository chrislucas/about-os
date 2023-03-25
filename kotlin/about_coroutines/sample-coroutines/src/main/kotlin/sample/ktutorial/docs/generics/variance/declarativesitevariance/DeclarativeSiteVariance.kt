package sample.ktutorial.docs.generics.variance.declarativesitevariance


import sample.generics.variance.DeclarativeSiteVariance.AbstractProducer
import sample.generics.variance.DeclarativeSiteVariance.WrapperAbstractProducer
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


/**
 * @see sample.generics.variance.DeclarativeSiteVariance.AbstractProducer
 *
 * Veja que a interfaace acima não possui metodos que permitem definir valores (set)
 * somente metodos de leitura. E mesmo assim o Java nao permite fazer algo do tipo
 *
 * void notAllowed(AbstractProducer<String> str)
 *      AbstractProducer<Object> obj = str
 *
 * Para permitir que obj receba str podemos fazer isso
 *      AbstractProducer<? extends Object> obj = str
 *      ou
 *      AbstractProducer<?> obj = str
 *

 */

/*
    adicionando um limite superior, Any ou subtipos de Any
    Analogo ao <? extends Object> do Java
 */
private fun <T : Any> notAllowedWithUpperBoundSet(abstractProducer: AbstractProducer<T>) {
    // nao eh permitido para que nao haja erros de tipos em tempo de execucao
    // uma vez que a interface poderia receber metodos de consumer (set(T value))

    // val obj: AbstractProducer<Any> = abstractProducer

    val obj1: AbstractProducer<out Any> = abstractProducer;
    println(obj1.get())
}



private fun nowAllowedSet(abstractProducer: AbstractProducer<String>) {
    // nao permitido
    // val obj: AbstractProducer<Any> = abstractProducer

    val obj1: AbstractProducer<out Any> = abstractProducer;
    println(obj1.get())
}





/**
 *
 */

fun main() {
    notAllowedWithUpperBoundSet(WrapperAbstractProducer("teste"))
    notAllowedWithUpperBoundSet(WrapperAbstractProducer(123))
}