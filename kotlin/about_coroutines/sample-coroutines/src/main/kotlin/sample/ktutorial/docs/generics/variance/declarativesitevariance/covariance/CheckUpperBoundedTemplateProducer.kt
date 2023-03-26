package sample.ktutorial.docs.generics.variance.declarativesitevariance.covariance

private class UpperBoundedTemplateProducer<out T>(val producer: Producer<T>) {
    fun get(): T = producer.get()
}

private fun checkTemplateProducer(templateString: UpperBoundedTemplateProducer<String>) {
    val templateAny: UpperBoundedTemplateProducer<Any> = templateString
    println(templateAny.get())
}

fun main() {
    checkTemplateProducer(UpperBoundedTemplateProducer(ProducerImpl { "Text 123" }))
}