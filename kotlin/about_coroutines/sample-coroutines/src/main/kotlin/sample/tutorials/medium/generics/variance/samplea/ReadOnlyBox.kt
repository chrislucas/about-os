package sample.tutorials.medium.generics.variance.samplea

class ReadOnlyBox<out T>(private val provide: () -> T) {

    fun getValue(): T = provide()

    /*
        nenhum metodo é capaz de receber T por ser incerto o que se pode passar e como
        lidar com isso
     */

    // fun setGenericValue(value: T) {}
}


fun main() {
    val readOnLyInt = ReadOnlyBox { 0xff }
    println(readOnLyInt.getValue())
}