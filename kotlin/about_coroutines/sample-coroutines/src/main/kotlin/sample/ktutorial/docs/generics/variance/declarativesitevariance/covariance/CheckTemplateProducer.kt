package sample.ktutorial.docs.generics.variance.declarativesitevariance.covariance



private data class TemplateProducer<T>(val producer: Producer<T>)

private fun checkTemplateProducer(templateProducer: TemplateProducer<String>) {
    val templateAny: Producer<Any> = templateProducer.producer
    println(templateAny.get())
}

fun main() {
    checkTemplateProducer(TemplateProducer(ProducerImpl { "kevin o chris" }))
}