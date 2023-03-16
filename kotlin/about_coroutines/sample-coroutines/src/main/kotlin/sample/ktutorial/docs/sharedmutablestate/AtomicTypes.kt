package sample.ktutorial.docs.sharedmutablestate

import kotlinx.coroutines.*
import sample.ktutorial.atomictypes.SequenceAtomicInt
import sample.ktutorial.logCoroutineScope
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import kotlin.coroutines.CoroutineContext

/**
 * https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/atomic/package-summary.html
 *
 * Um resumo da descrição: Package java.util.concurrent.atomic Description
 *
 * Um pequeno toolkit de classes que suporta lock-free thread-safe programming
 *
 * Em essencia as classes desse pacote herdam a nocao de valores volatile, campos, e arrays de elementos que
 * provem uma condicao de atualizacao atomica
 *
 * What does "atomic" mean in programming?
 * https://stackoverflow.com/questions/15054086/what-does-atomic-mean-in-programming
 *  - Signigicado de operacao atomica: Uma unica operacao por vezes pode alterar o valor de uma variavel
 *      - Isso eh importante para sistemas multithread que tentam acessar uma variavel ao mesmo tempo
 *
 *  A explicacao mais votada é muito interessante
 *
 *  long foo = 65465498L
 *      - acima ocorre 2 operacoes separadas, a que define os primeiros 32bits da variavel long
 *      e a que define os demais 32 bits
 *      - Isso permite que uma thread pode ler a informacao/estado intermediaria, um inteiro de 32bits
 *
 *  Fazer a operacao atomica consistem em usar um mecanismo sincronizado a fim de garatir que
 *  a operacao seja vista por qualquer outra thread, como unica, nao dividida em partes (como citada acima)
 *
 *      - Isso permite que qualquer thread veja a informacao em variavel foo ou antes de ocorrer a definicao
 *      ou depois dela ocorrer, como uma operacao unica e nao ter acesso a um valor intermediario
 *
 *      - Um jeito de realizar isso e utilizando volatile
 *          - https://docs.oracle.com/javase/tutorial/essential/concurrency/atomic.html
 * @see Volatile
 *
 *
 */
suspend fun fakeComplexTask(action: suspend () -> Unit) = coroutineScope {
    repeat(100) {
        launch {
            action()
        }
    }
}

private fun changeAtomicInt(coroutineContext: CoroutineContext = Dispatchers.Unconfined) = runBlocking {
    /**
     * https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/atomic/AtomicInteger.html
     *
     * Um int value que pode ser atualizado atomicamente
     *
     * AtomicInteger é usado em aplicacoes que se necessita de um
     * contador que seja incrementado atomicamente, numa
     * situacao onde nao e possivel utilizar o Integer
     *
     */
    val atomicInt = AtomicInteger(1)
    withContext(coroutineContext) {
        fakeComplexTask {
            logCoroutineScope("Coroutine contando: $atomicInt")
            atomicInt.incrementAndGet()
        }
    }
}

private fun checkChangeAtomicIntWithIOContext() {
    changeAtomicInt(Dispatchers.IO)
}

private fun checkChangeAtomicIntWithDefaultContext() {
    changeAtomicInt(Dispatchers.Default)
}

private fun checkChangeAtomicIntWithUnconfinedContext() {
    changeAtomicInt()
}

private fun getAndTransform(atomicValue: AtomicLong, tranformer: (Long) -> Long) {

    /*
         Os efetios em memoria por acessar e atualizar variaveis atomicas segue a regra para variaveis que volatile
         - Declarada No Modelo de memoria da especificacao da linguagem java
             - https://docs.oracle.com/javase/specs/jls/se7/html/jls-17.html#jls-17.4
               - get
                   -tem os efeitos em memoria de leitura de uma variavel volatile
               - set
                    -tem o efeito em memoria de escrever numa variavel volatile
               - lazyeSet
                    - Tem o efeito em memoria de escrever numa variavel volatile, exceto que permite
                    reordenamento com acoes de memoria subsequente
     */

    do {
        val prev = atomicValue.get()
        val next = tranformer(prev)
    } while (!atomicValue.compareAndSet(prev, next))
}

private fun checkGetANdTrasform() {
    getAndTransform(AtomicLong(10)) { it -> it % 2 }
}


/*
    Atomic Access
    https://docs.oracle.com/javase/tutorial/essential/concurrency/atomic.html

    Em programacao. uma acao atomica eh aquela que acontece de uma unica vez, sem ser separada em operacoes distintas
    em que uma dessas operacoes possa ser interceptada por exemplo.

    Exemplo: Um operacao/acao A modifica as propriedades de um objeto S, esse processo é atomico se ele começa e terminar sem
    a interferencia de nenhum outro processo que tenta acessa S

    Uma acaao atomica nao pode parar no meio, ou ela acontece comppletamente ou nao acontece, nenhum resultado é notado
    ate que a operacao atomica tenha terminado.

    Até a operacao de incremento com o operador ++ nao é uma operacao atomica, Simples expressoes podem ser definiidas
    por acoes complexas que podem se decompor em outras acoes. No entanto podemos definir acoes que sao atomicas

        - ler e escrever sao operacoes atomicas para variaveis de referencia e tipos primitivos (exceto ong e double)
        - Ler e escreever sao operacoes atomicas para todas as variavels declaradas volatile (incluindo long e double)

    Operacoes atomicas nao podem ser intercaladas, portanto estão seguras das threads, o que nao elimina a
     necessidade de sincronizar acoes atomicas

        - O uso de variaveeis volateis reduz riscos de consistencia de memoria porque qualquer escrita em variaveis
        volateis estabelece uma relacao anterior de leitura subsequente dessa mesma variavel

        - Alteracoees de uma variavel volatil sao sempre visiveis para outras threads

        - quando uma thread le uma variavel volatil ela nao ve somente o valor mais recente, também ve os efeitos
        colaterais que levaram aquela alteracao

        - Usar variaveis atomicas e mais eficiente do que acessar varaives atraves da expressao synchronized
 */
// a anotacao abaixo nao eh aplicavel para variaveis locais
@Volatile
var volatileInt = 1

private fun changeVolatileInt(coroutineContext: CoroutineContext = Dispatchers.Unconfined) {
    runBlocking {
        withContext(coroutineContext) {
            fakeComplexTask {
                logCoroutineScope("Coroutine contando: $volatileInt")
                volatileInt += 1
            }
        }
    }
}

private fun checkChangeVolatileInt() {
    //changeVolatileInt(Dispatchers.Default)
    changeVolatileInt(Dispatchers.Unconfined)
}

private fun checkNextAtomicInt() = runBlocking {
    val nextInt = SequenceAtomicInt()
    withContext(Dispatchers.Unconfined) {
        fakeComplexTask {
            logCoroutineScope("Coroutine contando: ${nextInt.next()}")
        }
    }
}

fun main() {
    checkGetANdTrasform()
}