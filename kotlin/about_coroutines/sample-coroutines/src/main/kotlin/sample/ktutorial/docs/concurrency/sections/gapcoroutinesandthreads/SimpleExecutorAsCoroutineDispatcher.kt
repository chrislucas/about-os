package sample.ktutorial.docs.concurrency.sections.gapcoroutinesandthreads

import kotlinx.coroutines.*
import sample.ktutorial.docs.logCoroutineScope
import java.util.concurrent.Executors


/**
 * https://medium.com/androiddevelopers/bridging-the-gap-between-coroutines-jvm-threads-and-concurrency-problems-864e563bd7c
 *
 * Dispatcher e Thread pools
 *
 * Podemos executar uma coroutine num pool de thread convertendo-a numa CoroutineDispatcher
 * usando a extension function Executor.asCoroutineDispatcher.
 *
 * - Dispatcher.Default
 *  @see createDefaultDispatcher
 *  @see sample.ktutorial.docs.ctxanddispatchers.checkDispatchers
 *  - por padrao DefaultScheduler é usado
 * - Dispatchers.IO
 *  @see Dispatchers.IO
 *  - Tambem usa o DefaultScheduler
 *  - permite ao menos a criacao de 64 threass
 *  - IO e default estao implicitamente ligados pois usam o mesmo pool de thread
 *  - Isso tras uma questao. Qual o overhead ao executar a funcao contrutora de coroutines withContext
 */


private val executor = Executors.newSingleThreadScheduledExecutor {
    Thread(it, "scheduler").apply { isDaemon = true }
}

private fun excutorAsCoroutineDispatcher() {
    runBlocking {
        val dispatcher = executor.asCoroutineDispatcher()
        val s = async(dispatcher) {
            "hello world!!!"
        }
        logCoroutineScope(s.await())
        dispatcher.close()
    }
}


/**
 * Threads and withContext performance
 *
 * Context switch
 * https://en.wikipedia.org/wiki/Context_switch
 *
 * "Em computacao, a troca de contexto (context switch) e o processo de armazenar
 * o estado de um processo ou Thread, tal que possa ser restabelecido e entregue
 * a execucacao do ponto que foi interrompido.
 *
 * Isso permite que multiplos prcessos compartilhem uma CPU, sendo essa a caracteristic
 * principal de um sistema operacional multitask
 * "
 *
 * O conceito de context switch é variavel. E um contexto de multitask, refere-se
 * ao processo d armazenar o estado do sistema para uma task, tal que a task
 * possa ser pausada e outra task ser liberada para execucao.
 *
 * Context switch pode ocorrer como o resultado de uma interrupcao, como
 * quando uma task precisa acessar um disco, liberar tempo de CPU para outra task.
 *
 * Alguns sistemas tambem requerem um context switch para move tasks entre user mode e kernel
 * mode. A troca de processos ou contexto (context/process switch) pode impactar negativamente
 * no desempenho do sistema
 *
 * Context Switch:
 * https://medium.com/androiddevelopers/bridging-the-gap-between-coroutines-jvm-threads-and-concurrency-problems-864e563bd7c
 *
 * "Nao eh um recurso barato, O S.O precisa guardar e recuperar o contexto de execucao de um task
 *  e a CPU precsa gastar tempo agendando threads ao inves de executar o app."
 *
 */


fun main() {
    excutorAsCoroutineDispatcher()
}