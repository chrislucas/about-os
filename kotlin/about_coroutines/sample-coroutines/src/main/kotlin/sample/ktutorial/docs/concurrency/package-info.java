package sample.ktutorial.docs.concurrency;


/**
 * Overview
 * https://kotlinlang.org/docs/multiplatform-mobile-concurrency-overview.html#global-properties
 *
 *
 * Android
 * Bridging the gap between coroutines, threads, and concurrency problems
 * https://medium.com/androiddevelopers/bridging-the-gap-between-coroutines-jvm-threads-and-concurrency-problems-864e563bd7c
 *
 * fun syncCals() {
 *   CoroutineScope(Dispatcher).launch {
 *       val value = syncCalculation()
 *       saveResult(value)
 *   }
 * }

 *
 * O exemplo de codigo acima cria uma coroutine atraves do construtor
 * launch e executa a funcao lambda que por sua vez executa um algoritmo
 * ficticio sincrono. Essa funcao lambda eh despachada e sua execucata agenda
 * num Pool de Thread gerenciado pela biblioteca que implementa o recurso
 * de coroutine.
 *
 *  - O codigo sera executado por uma thread num pool de thread em algum momento
 *  conforme a politica de pool de thread
 *
 *  - Codigo de exemplo é executado por uma thread porque ele nao suspende a
 *  coroutine
 *
 *  - Eh possivel executar uma mesma coroutine em thredas diferentes
 *  se a execucao for movida para um dispatcher diferente ou se o bloco
 *  da funcao contem codigo que deve retornar ou suspender em um dispatcher
 *  que usa um pool de thread
 *
 *
 *  Under the hood
 *
 *  A Dispatcher é responsavem por despachar a execucao de uma coroutine para uma thread
 *
 *  - QUando um CoroutineDispatcher é usado, ela intercepta a coroutine usando
 *  o metodo interceptContinuation
 *  (https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-continuation-interceptor/intercept-continuation.html)
 *  .
 *  Esse metodo encapsula uma Continuation em uma DispatchedContinuation
 *  (https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/common/src/internal/DispatchedContinuation.kt)
 *
 * - CoroutineDispatcher implemena ContinuationInterceptor
 *
 * - Para o mecanismo de coroutines funcionar, o compilador cria uma estrutura de "estado de máquina",
 * e a informacao sobre o estado (o que precisa ser executado na sequencia) é mantido no
 * objeto Continuation
 *
 * - No caso de uma continuation necessitar ser executada num dispatcher diferente, o
 * DispatcherContinuation possui o metodo resumeWith que é encarregado de despachar
 * a coroutine para a continuacao adequada
 *
 * - Alem disso, uma DispatcherContinuation é uma classe filha de DispatcherTask que, por sua
 * vez é uma classe filha de SchedulerTask que por fim implementa a interface Runnable
 *  - a classe DispatchedTask implementa o método run como final, ou seja as classes filhas nao podem modificar
 *  - a classe DispatcherContinuation somente executa o metodo run
 *  - Tudo isso para possibilitar que DispatcherContinuation possa ser executada numa Thread
 *  - Quando um CoroutineDispatcher é especificada, a coroutine é trasnformada numa DispatchedTask
 *  que eh despachada para ser executada numa Thread como uma Runnable
 *
 *
 * * * * * * * * * * * * * * * * * *  * * *  * * *  * * *
 *
 * The suspend modifier — under the hood
 * https://manuelvivo.dev/suspend-modifier
 *
 * * * * * * * * * * * * * * * * * *  * * *  * * *  * * *
 *
 * Kotlin/Native Memory Management Roadmap
 * https://blog.jetbrains.com/kotlin/2020/07/kotlin-native-memory-management-roadmap/
 *
 *
 * What does the suspend function mean in a Kotlin Coroutine?
 * https://stackoverflow.com/questions/47871868/what-does-the-suspend-function-mean-in-a-kotlin-coroutine#
 */