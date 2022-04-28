package sample.book.kcoroutines.tutorials.suspendfunworks

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * https://kt.academy/article/cc-suspension
 * Criando um exemplo sem a necessidade de se criar thread para retornar (resume)
 * a suspensão de uma coroutine.
 *
 * Thread nao eh um recurso barato, entao uma forma melhor de se fazer isso é usando
 * um Scheduler
 */

private val executor = Executors.newSingleThreadScheduledExecutor {
    Thread(it, "scheduler").apply { isDaemon = true }
}


/**
 * O resultado da funcao abaixo é diferente do exemplo criando uma Thread.
 */
private suspend fun checkExecutor() {
    println("Antes de suspender a coroutine")
    suspendCoroutine<Unit> {
        println("Suspendendo $it\nExecutor: $executor")
        executor.schedule({
            it.resume(Unit)
        }, 3000, TimeUnit.MILLISECONDS)
        println("Liberando $it\nExecutor: $executor")
    }
    println("Retornando para a coroutine")
}

suspend fun main() {
    checkExecutor()
}
