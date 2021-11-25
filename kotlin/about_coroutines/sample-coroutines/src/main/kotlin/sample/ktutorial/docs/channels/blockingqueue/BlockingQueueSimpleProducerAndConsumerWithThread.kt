package sample.ktutorial.docs.channels.blockingqueue

import sample.ktutorial.logCoroutineScope
import java.util.concurrent.*
import kotlin.concurrent.thread

/*
    https://www.baeldung.com/java-blocking-queue

   Existem 2 tipos de BlockingQueue
   1) Unbounded Queue - pode crescer de forna indefinida
   2) Bounded Queue - possui um tamanho máximo
 */

/*
    O construtor padrao de LinkedBlockingQueue construie uma BlockingQueue
    de tamanho Integer.MAX_VALUE. Todas as operacoes
    que adicionamm um elemento a uma UNBOUNDED QUEUE nao serao bloqueadas o que
    permite um crscimento muito grande/
 */
val unboundedQueue: BlockingQueue<String> = LinkedBlockingQueue()

/*
    Criamos uma BlockingQueue com tamanho limitado usando a classe
    LinkedBlockingDeque(int capacity).  Se um producer tentar adicionar um elemento a uma
    fila cheia, dependendo do metodo que for usado
        - offer(), add(), put()
    a operacao sera bliqueada ate ter espaco disponivel.

    API
    - adding
        - add() return true se ocorrer a insercao, do contrario lanca uma excecao
        - put() insere um elemento na ila, caso nao tenha espaco ela aguarda um espaco ser liberado
        - offer() return true se a insercao ocorrer, do contrario false
        - offer(E e, long timeout, TimeUnit unit) - tenta inserir o elemento na fila e aguarda, se necessario
        aguarda o tempo definido no argumento timeout por um espaco livre
    - Retrieving Elements
        - take() - espera pelo elemento no comeco da fila e o remove. se a fila estiver vazia, a operacao eh bloqueada
        ate ter um elemento praa remover
        - poll(long timeout, TimeUnit unit) - recupera e remove o elemento no comeco da fila, espera o existir
        um elemento no começo da fila pelo tempo definido no argumento timeout quando necessario. retorna
         null apos o timeout

     Esses sao os metodos importantes da api da interface BlockingQueue quando construimos producer-consumer programs
 */
val boundedQueue: BlockingQueue<String> = LinkedBlockingDeque(12)

/*
    Vamos implementar um simples exemplo de Procuder e Consumer
    - Vamos criar um producer de numeros aleatorios entre 0 e 100 e adicionar
    esses numeros a uma BlockingQueue
    - Usaremos 4 producer threads e para adicionar a Queue usaremos o metodo put

    - Ponto importante, nao podemos permitir que as threads de consumer esperem por um elemento
    um tempo indefinido

    - uma boa tecnica para um producer sinalizar  um consumer que nao a mais mensagens para
    processar e enviarndo uma mensagem "especial" denominada de POISON PILL., precisamos enviar
    uma poison para cada consumer. Assim que o consumer receber a poison pill ele deve finalizaar seu consumo

 */

val defaultPoisonPill: Int = 100 //-(1 shl 7)

class NumberProducer(
    private val numberQueue: BlockingQueue<Int>,
    private val poisonPill: Int = Int.MIN_VALUE, private val poisonPillPerProducer: Int
) : Runnable {

    override fun run() {
        try {
            generateNumbers()
        } catch (e: InterruptedException) {
            println(e.message)
            Thread.currentThread().interrupt()
        }
    }

    @Throws(InterruptedException::class)
    private fun generateNumbers() {
        val limit = 100
        for (i in 0..limit) {
            numberQueue.put(ThreadLocalRandom.current().nextInt(limit))
        }

        for (i in 0..poisonPillPerProducer) {
            numberQueue.put(poisonPill)
        }
    }
}

/*
    Cada consumer tomara um elemento de BlockingQueue usando o metodo take(), causando
    um bloqueio no mecanismo de recuperacao ate que tenha um elemento na fila. Depois
    disso, deve verificar se o elemento removido da pilha eh uma poisonPill
 */

class NumberConsumer(
    private val queue: BlockingQueue<Int>,
    private val poisonPill: Int = Int.MIN_VALUE
) : Runnable {
    override fun run() {
        try {
            while (true) {
                val value = queue.take()
                val threadName = Thread.currentThread().name
                if (value == poisonPill) {
                    println("Finish consumer: Thread name $threadName")
                    return
                }
                println(
                    String.format(
                        "Consumer - Thread name: %s | Value from Blocking Queue: %d",
                        threadName,
                        value
                    )
                )
            }
        } catch (e: InterruptedException) {
            println(e.message)
            Thread.currentThread().interrupt()
        }
    }
}


private fun checkBoundedQueue() {
    val producers = 4
    val consumers = Runtime.getRuntime().availableProcessors()
    val poisonPillPerProducer = consumers / producers
    println("Producers: $producers, Consumers: $consumers")
    start(producers, consumers, defaultPoisonPill, poisonPillPerProducer, LinkedBlockingDeque(10))
}

private fun start(
    producers: Int,
    consumers: Int,
    poisonPill: Int,
    poisonPillPerProducer: Int,
    queue: BlockingQueue<Int>
) {
    for (i in 1..producers) {
        /*
         thread {
             NumberProducer(queue, poisonPill, poisonPillPerProducer)
         }.start()

         */

        Thread(NumberProducer(queue, poisonPill, poisonPillPerProducer)).start()
    }

    for (i in 1..consumers) {
        /*
            thread {
                NumberConsumer(queue, poisonPill)
            }.start()
         */
        Thread(NumberConsumer(queue, poisonPill)).start()
    }

    val mod = consumers % producers
/*
    thread {
        NumberProducer(queue, poisonPill, poisonPillPerProducer + mod)
    }.start()
 */
    Thread(NumberProducer(queue, poisonPill, poisonPillPerProducer + mod)).start()
}

fun main() {
    checkBoundedQueue()
}