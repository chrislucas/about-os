package sample.ktutorial.basics;

/**
 * https://kotlinlang.org/docs/coroutines-basics.html#structured-concurrency
 * https://github.com/Kotlin/kotlinx.coroutines/blob/master/ui/coroutines-guide-ui.md
 * https://kotlin.github.io/kotlinx.coroutines/
 * https://github.com/Kotlin/KEEP/blob/master/proposals/coroutines.md
 *
 * How does suspension work in Kotlin coroutines?
 * https://kt.academy/article/cc-suspension
 *
 *
 * Flow
 * https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/
 *
 * What does the suspend function mean in a Kotlin Coroutine?
 * https://stackoverflow.com/questions/47871868/what-does-the-suspend-function-mean-in-a-kotlin-coroutine
 *
 * Do suspend functions execute in another thread and come back with the result?
 * https://stackoverflow.com/questions/61379260/do-suspend-functions-execute-in-another-thread-and-come-back-with-the-result
 *
 * structured concurrency
 * https://proandroiddev.com/structured-concurrency-in-action-97c749a8f755
 * https://elizarov.medium.com/structured-concurrency-722d765aa952
 * https://en.wikipedia.org/wiki/Structured_concurrency
 *
 * Coroutines seguem o rpincipui "Strucutred concurrency"
 *  - Uma nova coroutine pode ser lancada numa CoroutineScope especifica
 *  - Essa por sua vez delimita o 'tempo de vida' da coroutine
 *      - runBlocking eh um exemplo de construtor de CoroutineScope
 *
 * */