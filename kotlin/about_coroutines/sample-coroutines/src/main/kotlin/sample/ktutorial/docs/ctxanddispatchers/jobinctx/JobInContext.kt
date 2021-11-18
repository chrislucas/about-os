package sample.ktutorial.docs.ctxanddispatchers.jobinctx

import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking

/*
    https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html#job-in-the-context

    #Job
    https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html
    - Um Executor(Job) de tarefas em background
    - Conceitualmente um Job é um executor de instruções (o texto original trata um job como thing) CANCElÄVEL
    que possui un ciclo-de-vida que se encerra no estado de COMPLETATION

    -Jobs podem ser dispostos numa estrutura hierarquica pai-filho onde ao cancelar um job pai todos os fillhos
    sao cancelados de forma recursiva

    - O Cancelamento de um Job filho através de uma exception diferente de CancellationException cancela imediatamente
    o Job pai e consequentemente todos os demais Jobs filhos. Esse comportamento pode ser personalizado através do metodo
    SupervisorJob(https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-supervisor-job.html)


    # SupervisorJob
    https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-supervisor-job.html

    - Metodo que criar um Supervisor Job Object num estado ativo
    - Filhos de SupervisorJob podem falhar de forma independente entre si
        - Uma falha o cancelamento de um Job Filho nao causa a falha do supervisor Job nem afeta os demais filhos\
        - Um supervisor Job pode implementar uma politica personalizada para manipular falhas de Jobs Filhos

            - 

 */

private fun check() = runBlocking {

    // coroutineContext.get()
    println("${coroutineContext[Job]}")
    println("${coroutineContext[kotlinx.coroutines.Job.Key]}")

}


fun main() {
    check()
}