package sample.book.kcoroutines.tutorials.suspendfunworks

import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * https://kt.academy/article/cc-suspension
 *
 * - "suspending a coroutine means stopping it in the middle. it is similar
 * to stopping a video game: you save at a checkpoint, turn off the game
 * ... Then when you'd like to continue some time later, your turn on the game and
 * resume from the saved checkpoint ..."
 *
 * - "When the coroutines are suspended they return a Continuation. It is like a save
 * in a game"
 *
 *
 * - "It is very differente from a thread, which cannot be saved, only blocked.
 * A coroutine is much more powerful"
 *      - "When suspended, it does not consume any resources"
 *      - A coroutine can be resumed on a different thread
 *      - A continuation can be serialized, deserialized and resumed (theorically)
 */

/**
 * suspending functions are the ones that can suspend a coroutine
 *  - They must be called from  a coroutine or another suspend function
 *  - They need to have something to suspend
 */


suspend fun main() {
    /**
     * suspend fun main
     * O kotlin ira iniciar a funcao main numa coroutine
     */
    println("Antes de suspender a coroutine")
    /**
     * O codigo como está não termina de executar,
     * uma vez que a coroutine que foi criada para executar
     * a funcao main foi suspensa.
     *
     * Para terminar a execucao precisamos "liberar" a coroutine.
     * Fazemos isso através de uma instancia de Continuation executando
     * a funcao resume(T).
     *
     * A funcao suspendCoroutine e projetada de tal forma, que
     * possibilita usar uma Continuation antes de suspender a funcao.
     *
     * Depois da chamada de suspendCoroutine seria impossivel, entao
     * a funcao lambda passada como argumento para suspendCoroutine
     * é executada antes da coroutine ser suspensa
     */
    val s = suspendCoroutine<Int> { continuation ->
        println("suspendendo a coroutine.\nContinuation[$continuation]")
        //continuation.resumeWith(Result.success(Unit))
        continuation.resume(12)
        println("liberando a coroutine.\nContinuation[$continuation]")
    }
    println("Depois de libera-la $s")


}