package sample.os
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {

    /**
     * Dispara uma nova coroutine sem bloquear a thread atual onde
     * ela foi disparada e retorna uma referencia para coroutine
     * como um Job
     * {@link Job}
     *
     * @param context: CoroutineContext = EmptyCoroutineContext
     *
     *
     * @param start: CoroutinesStart = CoroutineStart.DEFAULT
     *
     * @param block: suspend CoroutinesScope.() -> Unit
     * */
    val job = GlobalScope.launch {
        // funcao com o comportamento similar a Thread.sleep(ms)
        // com a vantagem de nao bloquear a thread mas suspender a corotina
        delay(200L)
        println("Inside Launch: ${0xff}")
    }
    println("Outside: ${0xcc}")
    // THread sleep bloqueia a Thread principal por 500ms e manter a JVM ativa
    Thread.sleep(500L)

    job.let {
        it.children.let {
            seq ->
            if (seq.count() > 0) {
                println("Loggin Job children")
                seq.iterator().forEach { job -> printJobData(job) }
            }
        }
        println("Logging job itself")
        printJobData(it)
    }
}

fun printJobData(job: Job) {
    println("${job.isActive}, ${job.key}, ${job.isCompleted}")
}
