package sample.book.kcoroutines.tutorials.suspendfunworks;


/**
 *
 * https://kt.academy/article/cc-suspension
 *
 * "Suspender uma coroutine significa para-la no meio da execucao. Similar a parar
 * um video game: Savamos o jogo num checkpoint, desligamos o game e depois de algum
 * tempo voltamos a partir do checkpoint"
 *
 * Quando uma coroutine é suspensa ela retorna uma instancia de Continuation. Continuation
 * é como o checkpoint usado na analogia do video-game
 *
 * Suspending a coroutine, not a function
 * - "O que é possível fazer é suspender uma coroutine não uma função"
 * - "Suspending functions nao sao coroutines, sao funcoes que podem suspender coroutines"
 *
 * Summary
 * 1 - suspendCoroutine e suspendCancellableCoroutine chamam a função
 *  "suspendCoroutineUninterceptedOrReturn" implementada no arquivo Intrinsics.kt
 *  Essa que é uma funcao primitiva
 *
 *
 *  2 - Existe uma otimizacao na implementacao que ao perceber que entre a suspensao e o
 *  retorno da coroutine ha um intervalo curto a suspensao nao ocorre
 *
 *  3 - suspend main function é um caso especial. O compilador kotlin inicia a funcao
 *  em uma coroutine
 *
 */