package sample.tutorials.medium.generics.variance.samplea

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import sample.generics.variance.StaticModels.AutoCompleteFlexibleTextfield
import sample.generics.variance.StaticModels.AutoCompleteTextField
import sample.generics.variance.StaticModels.AutoCompleteTextArea
import sample.generics.variance.StaticModels.BaseTextField
import sample.generics.variance.StaticModels.FlexibleTextField
import sample.generics.variance.StaticModels.TextArea
import sample.generics.variance.StaticModels.TextField
import sample.tutorials.medium.generics.variance.samplea.Animal.*

/*
    https://proandroiddev.com/understanding-type-variance-in-kotlin-d12ad566241b

    Variance -
        "Relacao entre tipos genericos que compartilham a mesmo tipo-base mas com tipo de argumento diferente "
        - Exemplo List<Any>, o tipo base é List e o tipo do argumento Any

    Invariance - Class<T>
    Covariance - Class<out T> - <? extends T>
    ContraVariance - Class<in T> - <? super T>
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

    fun producer(producerTextField: ReadOnly<TextField>) {
        runBlocking {
            val textField = flow<TextField> {
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
 * Contravariance (Reversed subtyping relations)
 *  - Uma classe generica e chamada contravariante no tipo de parametro quando
 *  ocorre o seguinte:
 *      Class<A> eh subtipo de  Class<B> se B e subtipo de A
 *      Exemplo
 *
 *      Consumer<in T>
 *          fun consumer(value: T)
 *
 *      Consumer<Animal> e subtipo de Consumer<Cat> porque Cat e Subtipo de Animal
 *
 *      Consumer tem como parametros T e SUPERTIPOS de T e o metodo consumer tem como
 *      limite superior SUPERTIPO de T
 *
 *      ------------------------------------------------------------------------------
 *      WriteOnly<TextField> e subtipo de
 *          - WriteOnly<FlexibleTextField>
 *          - WriteOnly<AutoCompleteTextField>
 *
 * @see WriteOnly
 */

private fun checkContravariance() {

    fun reverseSubtypingRelationI() {


        /*
             Limite inferior FlexibleTextField: Posso passar para o metodo write uma instancia de
                 - FlexibleTextField
                 - Filhos de FlexibleTextField, diretos ou indiretors
         */
        val writeOnlyFlexibleTextField: WriteOnly<FlexibleTextField> = object : WriteOnly<TextField> {
            override fun write(value: TextField) {
                println(value)
            }
        }
        /*
            writeOnlyFlexibleTextField.write(AutoCompleteTextField("#1", "Flexible TextField #1"))/
         */
        writeOnlyFlexibleTextField.write(
            FlexibleTextField(
                "#1",
                "Flexible TextField #1",
                "I am"
            )
        )

        writeOnlyFlexibleTextField.write(
            AutoCompleteFlexibleTextfield(
                "#1",
                "AutoCompleteFlexibleTexfield #1",
                "AutoCompleteFlexibleTexfield"
            )
        )
    }

    fun reverseSubtypingRelation2() {
        val writeOnlyAutoCompleteTexField: WriteOnly<AutoCompleteTextField> = object : WriteOnly<TextField> {
            override fun write(value: TextField) {
                println(value)
            }
        }

        // writeOnlyAutoCompleteTexField.write(BaseTextField("", ""))
        // writeOnlyAutoCompleteTexField.write(TextField("", ""))
        writeOnlyAutoCompleteTexField.write(AutoCompleteTextField("#1", "AutoComplete TextField #1"))
    }

    fun reverseSubtypingRelation3() {

        /*
            Limite interior: TextField
            Posso passar para o metodo write as seguintes instancias
                - TextField
                - Filhos de TextField, diretos e indiretos
         */
        val writeOnlyTextFieldA: WriteOnly<TextField> = object : WriteOnly<TextField> {
            override fun write(value: TextField) {
                println(value)
            }
        }

        // writeOnlyTextFieldA.write(BaseTextField("#1", "BaseTextField #1"))

        arrayOf(
            TextField("#1", "FlexibleTextField #1"),
            FlexibleTextField("#1", "FlexibleTextField #1", "I am"),
            AutoCompleteTextField("#1", "AutoCompleteTextField #1"),
        ).forEach {
            writeOnlyTextFieldA.write(it)
        }

        println("Teste writeOnlyTextFieldA")

        writeOnlyTextFieldA.write(
            TextField("#1", "FlexibleTextField #1")
        )

        writeOnlyTextFieldA.write(
            FlexibleTextField("#1", "FlexibleTextField #1", "I am"),
        )

        writeOnlyTextFieldA.write(
            AutoCompleteTextField("#1", "AutoCompleteTextField #1"),
        )

        writeOnlyTextFieldA.write(
            AutoCompleteFlexibleTextfield(
                "#1",
                "AutoCompleteTextField #1",
                ""
            ),
        )


        println("******************************************************************************************")


    }

    fun reverseSubtypingRelation4() {

        /*
            BaseTextField supertype TextField
            WriteOnly<BaseTextField> e subtipo de
                TextField supertype de FlexibleTextField, AutoCompleteTextField
                - WriteOnly<TextField>
                    - WriteOnly<FlexibleTextField>
                    - WriteOnly<AutoCompleteTextField>
         */
        val writeOnlyBaseTextField: WriteOnly<BaseTextField> = object : WriteOnly<BaseTextField> {
            override fun write(value: BaseTextField) {
                println(value)
            }
        }

        writeOnlyBaseTextField.write(TextField("#1", "TextField #1"))
        writeOnlyBaseTextField.write(FlexibleTextField("#2", "FlexibleTextField #2", "I am"))
        writeOnlyBaseTextField.write(AutoCompleteTextField("#2", "AutoCompleteTextField #2"))
        writeOnlyBaseTextField.write(BaseTextField("#1", "BaseTextField #1"))
    }


    fun reverseSubtypingRelation5() {

        /*
            BaseTextField e supertipo de TextField, portanto:
            WriteOnly<BaseTextField> e subtipo de
                    TextField e supertipo de FlexibleTextField, AutoCompleteTextField e TextArea, portanto :
                -  WriteOnly<TextField> e subtipo de:
                    - WriteOnly<FlexibleTextField>
                    - WriteOnly<TextArea>
                      AutoCompleteTextField e supertipo de AutomCompleteTextArea
                    - WriteOnly<AutoCompleteTextField> e subtipo de:
                        WriteOnly<AutomCompleteTextArea>

         */
        // Limite superior: TextField
        var mutableFieldWriteOnly: WriteOnly<AutoCompleteTextField>
        mutableFieldWriteOnly = object : WriteOnly<TextField> {
            override fun write(value: TextField) {
                println(value)
            }
        }

        mutableFieldWriteOnly.write(TextArea("1#", "-", 10))
        mutableFieldWriteOnly.write(AutoCompleteTextField("1#", "-"))
        mutableFieldWriteOnly.write(FlexibleTextField("1#", "-", "-"))
        mutableFieldWriteOnly.write(TextField("1#", "-"))
        // nao compila por conta do limite superior
        // writeOnlyTextField.write(BaseTextField("1#", "-"))

        println("********************************")


        /*
            AutoCompleteTextField nao e um subtipo de FlexibleTextField entao
            WriteOnly<AutoCompleteTextField> nao e um subtipo de WriteOnly<FlexibleTextField>

            writeOnlyTextField = object : WriteOnly<FlexibleTextField> {
                override fun write(value: FlexibleTextField) {
                    println(value)
                }
            }

            AutoCompleteTextField nao e um subtipo de TextArea entao
            WriteOnly<AutoCompleteTextField> nao e um subtipo de WriteOnly<TextArea>

            writeOnlyTextField = object : WriteOnly<TextArea> {
                override fun write(value: TextArea) {
                    println(value)
                }
            }
         */

        mutableFieldWriteOnly = object : WriteOnly<BaseTextField> {
            override fun write(value: BaseTextField) {
                println(value)
            }
        }
        mutableFieldWriteOnly.write(TextArea("2#", "-", 15))
        mutableFieldWriteOnly.write(AutoCompleteTextField("2#", "-"))
        mutableFieldWriteOnly.write(FlexibleTextField("2#", "-", "-"))
        mutableFieldWriteOnly.write(TextField("2#", "-"))
        mutableFieldWriteOnly.write(BaseTextField("1#", "-"))

    }

    // reverseSubtypingRelation5()

    fun reverseSubtypingRelation6() {

        /*
            Limite superior e AutomCompleteTextArea
         */
        val writeOnlyAutomCompleteTextArea: WriteOnly<AutoCompleteTextArea> =
            object : WriteOnly<AutoCompleteTextField> {
                override fun write(value: AutoCompleteTextField) {
                    println(value)
                }
            }

        writeOnlyAutomCompleteTextArea.write(
            AutoCompleteTextArea(
                "#1",
                "#1",
                12
            )
        )

        println("********************************")

        /*
            Limite superior e AutoCompleteTextField
         */
        val writeOnlyAutoCompleteTextField: WriteOnly<AutoCompleteTextField> =
            object : WriteOnly<AutoCompleteTextField> {
                override fun write(value: AutoCompleteTextField) {
                    println(value)
                }
            }

        writeOnlyAutoCompleteTextField.write(AutoCompleteTextField("#1", "#1"))
        writeOnlyAutoCompleteTextField.write(
            AutoCompleteTextArea(
                "#2",
                "#2",
                13
            )
        )
    }

    reverseSubtypingRelation6()

}

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
    // checkAnotherCopy()
    // checkCovariance()
    checkContravariance()
}