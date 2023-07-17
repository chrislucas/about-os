package com.br.sample.tutorials.kdoc

/*
    https://kotlinlang.org/docs/inline-classes.html

    Por vezes é necessário criar classe que "encapsulam" alguns tipos (primitivos ou mais complexos), entretando
    isso gera um overhead (a classe por si só já tem um valor em bytes, somado a isso tem os seus atributos que tem
    um tamanho em bytes seja primitivo ou nao)

    Se o tipo encapsulado/wrapped for primitivoo impacto na performance é pior, pois tipos primitivos sao geralmente
    sao otimizados em tempo de execução, enquanto suas classes Wrapper (Integer, Boolean e afins) nao

    Para resolver isso, Kotlin introduz um tipo especical de class chamava inline class
        - Inline class sao um subconjunto de value-based classes
        - https://github.com/Kotlin/KEEP/blob/master/notes/value-classes.md


    Sobre overhead de cada tipo em java
    https://www.cs.princeton.edu/courses/archive/spring23/cos226/precepts/MemoryAnalysis.pdf
    https://stackoverflow.com/questions/726404/what-is-the-memory-overhead-of-an-object-in-java
 */


/*
    Para declarar  uma inline class para JVM usamos o modificador 'value'
    Uma classe inline deve ter uma unica propriedade no seu construtor primario.

    Em tempo de execucao, elas serao representadas usando essa unica propriedade

    // @JvmInline
    // value class WrapperPair<K, V>(private val k: K, private val v: V)
 */
@JvmInline
value class WrapperString(private val value: String)



@JvmInline
value class Wrapper<T>(private val value: T) {
    // testando a ideia de ter construtores secundarios para ver o que acontece
    constructor(arg: String, v: T) : this(v)
}


private fun checkInlineClass() {
    /*
        Em tempo de execucao nao existe a instancia da classe wrapper, a variavel recebe
        a propriedade diretamente
     */
    val integer = Wrapper(1)
    println(integer)
    val string = WrapperString("Ola, mundo")
    println(string)
}

/*
    Membros de inline class
    https://kotlinlang.org/docs/inline-classes.html#members

    Inline classes suportam algumas funcionalidades de classes regulares, permitindo por exemplo declarar
    propriedades e funcoes, ter um bloco init e construtores secundarois
 */

@JvmInline
value class UserSystem(private val nickname: String) {
    init {
        require(nickname.isNotEmpty()) {
            "Init: nickname is empty"
        }
    }

    constructor(first: String, last: String): this("$first $last") {
        require(first.isNotEmpty() && last.isNotEmpty()) {
            "Second construtor: nickname is empty"
        }
    }

    val length: Int
        get() = nickname.length

    fun show() {
        println("Hello, $nickname")
    }
}


private fun checkUserSystem() {
    val userSystem = UserSystem("a", "")
    println(userSystem.length)
    userSystem.show()
}


fun main() {
    checkUserSystem()
}