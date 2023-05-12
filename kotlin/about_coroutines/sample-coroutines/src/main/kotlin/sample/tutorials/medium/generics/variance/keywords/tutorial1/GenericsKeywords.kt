package sample.tutorials.medium.generics.variance.keywords.tutorial1

import sample.generics.variance.StaticModels.*

/**
 * https://medium.com/@manuchekhrdev/kotlin-generics-in-out-where-9181ddbef01f
 *  * IN Consumer
 *  * OUT Producer
 *
 * OUT
 * - A keyword out e usada para definir covariancia em classes e interfaces genericas.
 * Permite a cricao de subtipos de tipos genericos serem usados no lugar de supertipos
 *
 */

private interface Producer<out T> {
    /**
     * Nesse exemplo T é covariante e isso possibilite que a funcao producer
     * possa retornar T ou um subtipo de T
     */
    fun producer(): T
}

/**
 * IN
 * - a Keyword in eh utilziada para especificar tipos de parametros contravariante, podendo ser
 * utilizado como parametros de entrada de metodo
 */
private interface Consumer<in T> {
    fun consumer(value: T)
}


class Vet<T : Animal> : Consumer<T> {
    override fun consumer(value: T) {
        println(value.getType())
    }
}

class AnimalBasket : Consumer<Animal> {
    override fun consumer(value: Animal) {
        println(value.getType())
    }
}

sealed class Animal {
    abstract fun getType(): String
}

abstract class Canine : Animal() {
    override fun getType() = "Canine"
}

object Dog : Canine() {
    override fun getType() = "Dog"
}

object Wolf : Canine() {
    override fun getType() = "Wolf"
}

abstract class Feline : Animal() {
    override fun getType() = "Feline"
}

object Cat : Feline() {
    override fun getType() = "Cat"
}

object Lince : Feline() {
    override fun getType() = "Lince"
}

object Eagle : Animal() {
    override fun getType() = "Eagle"
}

private fun checkProducerIn() {
    val canineVet = Vet<Canine>()
    canineVet.consumer(Dog)
    canineVet.consumer(Wolf)

    val felineVet = Vet<Feline>()
    felineVet.consumer(Lince)
    felineVet.consumer(Cat)

    Vet<Eagle>().consumer(Eagle)
}

private fun checkBasket() {
    val animalBasket = AnimalBasket()
    animalBasket.consumer(Dog)
    animalBasket.consumer(Cat)
    animalBasket.consumer(Lince)
}


class Box<T> : Consumer<T> {
    override fun consumer(value: T) {
        println("Box: $value")
    }
}


private fun checkProducerTextField() {
    var box = Box<TextField>()
    box.consumer(FlexibleTextField("", "", ""))
    box.consumer(AutoCompleteTextField("", ""))
    box.consumer(AutoCompleteTextArea("", "", 2))
    box.consumer(TextField("", ""))

    // Box<T> sou aceita outras Box<T>
    box = Box<TextField>()

}

class FlexBox<in T> : Consumer<T> {
    override fun consumer(value: T) {
        println("Wrapper $value")
    }
}

private fun checkWrapper() {
    var box: FlexBox<AutoCompleteTextArea> = FlexBox<BaseTextField>()
    box.consumer(AutoCompleteTextArea("", "", 2))
    box.consumer(AutoCompleteCodeEditor("", "", "", 12))

    println("************************************************************************************")

    box = FlexBox<AutoCompleteTextField>()
    box.consumer(AutoCompleteTextArea("", "", 2))
    box.consumer(AutoCompleteTextArea("", "", 2))
    println("************************************************************************************")


    box = FlexBox<TextField>()
    box.consumer(FlexibleTextField("", "", ""))
    println("************************************************************************************")

    box = FlexBox<BaseTextField>()
    box.consumer(TextField("", ""))

}

class TextFieldProducer<T : TextField>(var fn: () -> T) : Producer<TextField> {
    override fun producer(): TextField = fn()
}

private fun checkTextFieldBuilder() {
    val textFieldProducer = TextFieldProducer { FlexibleTextField("", "", "") }
    println(textFieldProducer.producer())

    textFieldProducer.fn = { AutoCompleteFlexibleTextfield("", "", "") }
    println(textFieldProducer.producer())
}

private class GenericProducer<T>(private val externalProducer: () -> T) : Producer<T> {
    override fun producer(): T = externalProducer()
}

private fun checkGenericProducer() {
    var genericProducer: Producer<BaseTextField> = GenericProducer { TextField() }
    println(genericProducer.producer())

    genericProducer = GenericProducer { FlexibleTextField() }
    println(genericProducer.producer())

    genericProducer = GenericProducer { AutoCompleteTextArea() }
    println(genericProducer.producer())
}

private interface InvarianteProducer<T> {
    fun producer(): T
}

private class InvarianteGenericProducer<T>(private val fn: () -> T): InvarianteProducer<T> {
    override fun producer(): T = fn()
}

private fun checkInvarianteGenericProducer() {
    var genericProducer : InvarianteProducer<BaseTextField> = InvarianteGenericProducer { TextField() }
    println(genericProducer.producer())
    genericProducer = InvarianteGenericProducer { FlexibleTextField() }
    println(genericProducer.producer())
}


fun main() {
    //checkBasket()
    //checkProducerTextField()
    //checkTextFieldBuilder()
    checkInvarianteGenericProducer()
}