package sample.ktutorial.docs.flow.builders.sequence

/*
    https://kotlinlang.org/docs/flow.html

    sequence builder e interface Sequence
    https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.sequences/

   public interface Sequence<out T>
   Um tipo que representa uma "avaliação com atrasado" (represents lazily evaluated) de uma estrutura
   de dados do tipo colecao.


   Classificacao das operacoes em sequencia
   https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.sequences/#classification-of-sequences

   Stateless: Operacoes que nao requerem nenhum estado


 */


fun computing(): Sequence<Int> = sequence {
    for (i in 1..150) {
        /*
            Simulando um processo computacional que leva 1s por operacao
         */
        Thread.sleep(100)
        yield(i)
    }
}


private fun checkSequenceType() {
    val seq = computing()
    seq.forEach { value ->
        println(value)
    }
}


private fun checkSequenceFunDrop() {
    val seq = computing()
    /*
        Ext Fun: Retorna uma sequencia sem os n-esimos primeiros elementos
     */
    seq.drop(100).forEach { value ->
        println(value)
    }
}

fun main() {
    checkSequenceFunDrop()
}