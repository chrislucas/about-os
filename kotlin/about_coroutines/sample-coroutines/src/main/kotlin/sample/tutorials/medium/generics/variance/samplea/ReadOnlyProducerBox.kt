package sample.tutorials.medium.generics.variance.samplea


/*
    Producer
 */
class ReadOnlyProducerBox<out T>(private val provide: () -> T) {

    fun getValue(): T = provide()

    /*
        nenhum metodo é capaz de receber T por ser incerto o que se pode passar e como
        lidar com isso
        https://proandroiddev.com/understanding-type-variance-in-kotlin-d12ad566241b

        "para garantir segundranca sobre o tipo T, somente podemos usar T como retorno de um metodo"
     */

    // fun setGenericValue(value: T) {}
}


fun main() {
    val readOnLyInt = ReadOnlyProducerBox { 0xff }
    println(readOnLyInt.getValue())
}