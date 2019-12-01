package samples.kotlin

import samples.java.SampleFunInterface

interface SecurityBussinessEncryption {
    fun execute()
}

class PaymentOperation(private val operation: () -> SecurityBussinessEncryption) {
    fun executeOperation() {
        operation().execute()
    }
}

val op : SecurityBussinessEncryption = object : SecurityBussinessEncryption {
    override fun execute() {
        println("Aplicando algoritmos de criptografia sobre os dados")
    }
}

/**
 * O exemplo abaixo eh um estudo motivado a entender o que eh lambda expression em Java e em Koltin
 * e o que estamos fazendo quando escrevemos em kotlin Thread {}, porque essa sintaxe eh permitida
 * e a diferenca quando temos uma classe em Kotlin e essa sintaxe nao tem o mesmo comportamento
 *
 * Exemplo: Quando escrevo Class {}, isso significa que estou instanciando uma classe
 * cujo construtor tem uma funcao () -> Unit como argumento
 *
 * */

fun main() {
    /*
    * Abaixo temos um exemplo de uma classe Java que recebe uma interface
    * como argumento via construtor. Nessa situação, quando utilizamos
    * uma classe Java em um arquivo kt podemos usufruyi do recurso de Lambda expression
    * e asism dizer explicitamente qual interface estamos implementando
    * */
    // Construtor da Thread passando um Runnable como argumento
    Thread {
        Thread.sleep(2000L)
        println("Executando o metodo run da interface Runnable")
    }.start() // o metodo start executa o metodo run

    // Exemplo similar com uma classe minha de exemplo
    SampleFunInterface.CreditCardOperation {
        // aqui temos a execucao do metodo execute() da classe CreditCardOperation
        println("Executando o pagamento da fatura, aguarde ... :)")
    }.executePayment() // o metodo executePayument vai executar as instrucoes definidas dentro do bloco {}

    /*
    * Entretanto, se tentarmos utilizar essa mesma sintaxe numa classe em kotlin, nao teremos o resultado
    * parecido quando usamos uma classe em java porque as regras em kotlin dizem que essa sintaxe serve para
    * simplificar a criacao de um metodo que sera passado por argumento para outro metodo, nesse caso em especifico
    * um metodo construtor da classe PaymentOperation
    *
    * Antetirormente a classe PaymentOperation recebia uma interface, o que impossibilitava de usar a sintaxe Class {}
    * entao implementamos o construtor dessa classe de tal forma que ela receba um metodo que retorna uma instancia de
    * 'SecurityBussinessEncryption', assim podemos escrever na sintaxe Class {} como desejavamos
    *
    * */
    PaymentOperation { op }.executeOperation()
}