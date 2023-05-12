package sample.tutorials.medium.generics.variance.sitevariance


import sample.generics.variance.StaticModels.AutoCompleteCodeEditor
import sample.generics.variance.StaticModels.AutoCompleteFlexibleTextArea
import sample.generics.variance.StaticModels.AutoCompleteTextField
import sample.generics.variance.StaticModels.AutoCompleteTextArea
import sample.generics.variance.StaticModels.FlexibleTextField
import sample.generics.variance.StaticModels.TextField
import sample.generics.variance.StaticModels.AutoCompleteFlexibleTextfield
import sample.generics.variance.StaticModels.BaseTextField
import sample.tutorials.medium.sealedtest.Empty
import sample.tutorials.medium.sealedtest.State

/**
Declaration-site variance
https://proandroiddev.com/understanding-type-variance-in-kotlin-d12ad566241b
- Kotlin permite especificar o modificador de variance na definicao da interface ou classe
 * @see ReadOnly
 * @see WriteOnly
 *
 * Ao especificar na definicao da classe/interface o modificador de variancia, todas as metodos que
 * podem utilizar o tipo parametrizado como argumento ou retornar-o
 * class Clazz<in A, out R>
 *     fun invoke(value: T)
 *     fun process(value: T): R
 *

 *  - Isso diferencia o kotlin do Java onde usamos o wildcar em cada método que precisamos/queremos
 */

sealed class Animal(open val name: String)

data class Eagle(override val name: String) : Animal(name)

data class Bear(override val name: String, val weight: Double) : Animal(name)

private fun <T> copy(provider: List<T>, consumer: MutableList<in T>) {
    consumer.addAll(provider)
    println(consumer)
}

private fun <T> anotherCopy(provider: List<out T>, consumer: MutableList<in T>) {
    consumer.addAll(provider)
    println(consumer)
}


private fun checkUseSiteVariance() {
    val provider = listOf(
        FlexibleTextField("#1", "", ""),
        FlexibleTextField("#2", "", ""),
        FlexibleTextField("#3", "", "")
    )

    copy(provider, mutableListOf<TextField>())
}

private fun <T> useSiteVarianceMutableList(provider: MutableList<T>, consumer: MutableList<in T>) {
    consumer.addAll(provider)

    /*
        Nao compila
        provider.addAll(consumer)


     */


    println(consumer)
}

private fun checkUseSiteVarianceMutableList() {

    /*
        O modificador de variancia IN impoe um limite inferior do tipo que se pode passar.
        Ou passa-se um tipo T ou um supertipo T. Esse supertipo de T pode ser 1 nível acima
        ou mais, indo até o topo da hierarquia de superclasse. Veja o Exemplo do metodo
        test4
     */

    val mixedAnimals = listOf(
        Eagle("George"),
        Bear("Amanda", 234.3),
        Bear("Samantha", 322.3),
        Eagle("Leonard")
    )

    fun test() {
        copy(
            mixedAnimals,
            mutableListOf()
        )
    }

    fun test1() {
        val eagles = mixedAnimals.filterIsInstance<Eagle>()
        val bears = mixedAnimals.filterIsInstance<Bear>()
        /*
            Obviamente nao compila
            useSiteVariance(
                eagles,
                bears
            )
         */

        copy(
            eagles,
            mutableListOf<Animal>()
        )

        copy(
            bears,
            mutableListOf<Animal>()
        )
    }

    // test1()


    fun test2() {
        val mixedTextField : List<TextField> = listOf(
            TextField("", ""),
            FlexibleTextField("", "",  ""),
            AutoCompleteFlexibleTextfield("", "", ""),
            AutoCompleteFlexibleTextArea("", "", "")
        )

        /*
            Ambos as chamadas abaixo vai compilar, pois o metodo
            copy aceita como segundo parametro
            uma lista de TextField ou de um supertipo de TextField

         */

        copy(
            mixedTextField,
            mutableListOf<BaseTextField>()
        )
        println("*****************************************************************************")
        copy(
            mixedTextField,
            mutableListOf() // mutableListOf<TextField>()
        )
    }

    test2()

    fun test3() {
        val mixedTextField = listOf<BaseTextField>(
            TextField("", ""),
            FlexibleTextField("", "",  ""),
            AutoCompleteFlexibleTextfield("", "", ""),
            AutoCompleteFlexibleTextArea("", "", "")
        )

        /*
            Essa chamada nao funciona pois, ao passar uma lista de BaseTextField
             a assinatura do metodo fica assim explicitamento:
                - anotherCopy<BaseTextField>(List<BaseTextField, MutableList<in BaseTextField>)

             Assim o método só aceita
            anotherCopy(mixedTextField,
                mutableListOf<TextField>()
            )
         */

        anotherCopy<BaseTextField>(mixedTextField,
            mutableListOf() // mutableListOf<BaseTextField>
        )


    }

    // test3()

    fun test4() {

        /*
              Criamos uma lista do tipo AutoCompleteTextField que vai ser o limite inferior para o tipo de lista
              que se aceita passar como segundo argumento no metodo copy.

              A hierarquia de classe é

              BaseTextField
              - TextField
                - AutoCompleteTextField *
                  - AutoCompleteTextArea
                    - AutoCompleteCodeEditor

               Como o tipo da lista é AutoCompleteTextField
               - É aceitavel receber na lista os tipos AutoCompleteTextField, e seus subtipos
               - É aceitável receber como o segundo argumento no método copy:
                 - o próprio AutoCompleteTextField e seus supertipos, em ordem crescente TextField e BaseTextField

         */

        val mixed: List<AutoCompleteTextField> = listOf(
            AutoCompleteTextField("", ""),
            AutoCompleteTextArea("", "", 123),
            AutoCompleteCodeEditor("", "", "", Integer.MAX_VALUE - 1)
        )

        copy(
            mixed,
            mutableListOf<BaseTextField>()
        )

        copy(
            mixed,
            mutableListOf<TextField>()
        )

        copy(
            mixed,
            mutableListOf() // mutableListOf<AutoCompleteTextField>()
        )
    }

    test4()

}

private fun <T> add(consumer: MutableList<in T>, value: T) {
    consumer += value
}

interface InternalConsumer<in T> {
    fun consumer(value: T)
}

class HolderConsumer<T>(val consumer: InternalConsumer<T>)


private fun checkAdd() {
    val consumer = mutableListOf<TextField>()
    add(consumer, FlexibleTextField("#1", "", ""))
    add(consumer, AutoCompleteFlexibleTextfield("#1", "", ""))
    add(consumer, TextField("#3", ""))
    //add(consumer, BaseTextField("#1", ""))
    println(consumer)
    println("************************************************************")

    val consumer2 = mutableListOf<BaseTextField>()
    add(consumer2, BaseTextField("#1", ""))

}


fun main() {
    // checkUseSiteVariance()
    checkUseSiteVarianceMutableList()
}