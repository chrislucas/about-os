package sample.tutorials.medium.generics.variance.samplea


import sample.generics.variance.StaticModels.AutoCompleteFlexibleTextArea
import sample.generics.variance.StaticModels.BaseTextField
import sample.generics.variance.StaticModels.TextField
import sample.generics.variance.StaticModels.FlexibleTextField
import sample.generics.variance.StaticModels.AutoCompleteTextField
import sample.generics.variance.StaticModels.AutoCompleteFlexibleTextfield
import sample.generics.variance.StaticModels.TextArea
import sample.generics.variance.StaticModels.AutoCompleteTextArea


/*
    https://proandroiddev.com/understanding-type-variance-in-kotlin-d12ad566241b
 */


private fun testContravariance() {
    /**
     * @see sample.generics.variance.StaticModels
        TextField supertype de FlexibleTextField e AutoCompleteTextField
        WriteOnLy<TextField> e subtipo de
        - WriteOnly<FlexibleTextField>
        - WriteOnly<AutoCompleteTextField>
     */

    val writeOnlyTextFieldFromBaseTextField: WriteOnly<TextField> = object : WriteOnly<BaseTextField> {
        override fun write(value: BaseTextField) {
            println(value)
        }
    }

    println("Teste writeOnlyTextField")

    with(writeOnlyTextFieldFromBaseTextField) {
        /*
            Nao compila
            o metodo write só aceita o Supertipo ou filhos dele
         */
        // write(BaseTextField("#1", "BaseTextField #1"))

        write(TextField("#1", "TextField #1"))
        write(FlexibleTextField("#1", "FlexibleTextField #2", "I am"))
        write(AutoCompleteTextField("#1", "AutoCompleteTextField #2"))
        write(
            TextArea("#1", "TextArea #1", 20)
        )
    }

    println("******************************************************************************************")

    val writeOnlyTextField = object : WriteOnly<TextField> {
        override fun write(value: TextField) {
            println()
        }
    }

    with(writeOnlyTextField) {
        write(TextField("#2", "TextField from writeOnlyTextField"))
        write(FlexibleTextField("#2", "FlexibleTextField from writeOnlyTextField", ""))
        write(AutoCompleteTextField("#2", "AutoCompleteTextField from writeOnlyTextField"))
        write(TextArea("#2", "TextArea from writeOnlyTextField", 39))
    }

    println("******************************************************************************************")

    /**
        val writeOnlyBaseTextField: WriteOnly<BaseTextField> = writeOnlyTextField

        O codigo acima nao compila por que BaseTextField é um SUPERTIPO de TextField,e
        nessa situacao quando a classe usa o MODIFICADOR DE VARIANCIA IN, tornando a classe
        contravariante ocorre A INVERSÃO de relação de SUBTIPOS (Contravariance (reversed subtyping relations))
        https://proandroiddev.com/understanding-type-variance-in-kotlin-d12ad566241b

        BaseTextField e supertipo de TextField
        com a inversão
        WriteOnly<BaseTextField> e subtipo de WriteOnly<TextField>

        e por isso não podemos definir writeOnlyBaseTextField como writeOnlyTextField

         "Contravariancia pode ser visto como um espelho da covariancia"

     * @see ConsumerProvider
        C - consumer - contravariante (IN)
        P - Producer - Covariante (OUT)

        interface ConsumerProvider<in C, out P> {
            fun invoke(value: C): P
        }

     */



    println("******************************************************************************************")


    /*
        FlexibleTextField e supertipo de AutoCompleteFlexibleTexfield
            - e indiretamente supertiopo de AutoCompleteFlexibleTexArea
            - AutoCompleteFlexibleTexArea e subtipo de AutoCompleteFlexibleTexfield
     */

    /**
     * Inversao da relacacao de dependencia entre subtipo e super tipo
     *
     * Seja A supertipo de B, uma classe generica e chamada de contravariante quando o seguinte acontece
     *
     * - Class<A> e subtipo de class<B> se B for subtipo de A
     *      Exemplo
     *          - TextField e supertipo de FlexibleTextField
     *          - Class<TextField> e subtipo d Classe<FlexibleTextField>
     *              - aqui ocorre a inversao
     *
     *  @see WriteOnly
     *  - em WriteOnly<in T> o parametro T e o limite superior.
     *   fazendo com que o metodo write aeite T como parametros ou subtipos de T
     */

    var writeOnlyFlexibleTextField: WriteOnly<FlexibleTextField> = writeOnlyTextFieldFromBaseTextField

    println("Teste writeOnlyFlexibleTextField")

    with(writeOnlyFlexibleTextField) {

        /*
            TextArea nao e filho de FlexibleTextField
            write(
                TextArea("", "", "")
            )
         */
        write(
            FlexibleTextField("#2", "FlexibleTextField #2", "")
        )

        write(
            AutoCompleteFlexibleTextfield(
                "2#",
                "AutoCompleteFlexibleTextfield #2",
                ""
            )
        )

        write(
            AutoCompleteFlexibleTextArea(
                "2#",
                "AutoCompleteFlexibleTexArea #2",
                ""
            )
        )
    }


    println("******************************************************************************************")

    writeOnlyFlexibleTextField = writeOnlyTextField

    with(writeOnlyFlexibleTextField) {
        write(TextField("#2", "TextField From writeOnlyFlexibleTextField"))
        write(FlexibleTextField("#3", "FlexibleTextField from writeOnlyFlexibleTextField", ""))
    }


    println("******************************************************************************************")


    val writeOnlyAutoCompleteTextField: WriteOnly<AutoCompleteTextField> = writeOnlyTextFieldFromBaseTextField

    println("Teste writeOnlyAutoCompleteTextField")

    with(writeOnlyAutoCompleteTextField) {
        write(
            AutoCompleteTextField("3#", "AutoCompleteTextField #3")
        )

        write(
           AutoCompleteTextArea(
               "3#", "AutoCompleteTextArea #3", 2
           )
        )
    }

    println("******************************************************************************************")


}

fun main() {
    testContravariance()
}