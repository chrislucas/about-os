package sample.tutorials.medium.sealedtest


/*
    https://kotlinlang.org/docs/sealed-classes.html

    Resumo - Sealed Class and interfaces

    - Sealed class e interfaces representam hierarquias de classes que proveem
    mais controle sobre o relacionamento de heranca entre classes

    - Todas as subclasses diretas de uma sealed class sao conhecidas e tempo
    de compilacao e nenhuma aparece fora do modulo ou pacote que a sealed class
    foi definida

      - Exemplo de erro em tempo de compilacao ao tentar herdar de uma sealed class
      de outro package
      - Inheritor of sealed class or interface declared in
         package sample.tutorials.medium.generics.variance.sitevariance
         but it must be in package
         sample.tutorials.medium.sealedtest where base class is declared
    - O mesmo ocorre para sealed interfaces
        - Assim que compila-se um modulo com sealed inerface, nenhuma nova implementacao pode aparecer

    - De certa forma uma sealed classe/ e similar a um enum. O conjuto de tipos de que um
    enum pode ser definido limitado, assim como uma sealed class, mas cada constante de um enum
    existe somente como uma instancia unica
        - ao passo que uma subclasse de sealed class pode ter multiplas instancias, cada uma com
        seu proprio estado


 */

sealed class State

open class Empty: State()
object Idle: State()
object Start: State()
object Process: State()
object End: State()

/**
 * Considere o seguinte cenario:
 *  - uma biblitoeca contendo classes de Erros para lidar com os erros
 *  que a biblitoeca pode emitir. Se essas classes forem classes abstratas ou interfacace
 *  forem fornecedidas de forma publica, nada previne que o cliente da biblioteca
 *  implemente ou herde dessas classes. Entretanto, a biblioteca desconhece
 *  o comportamento de implementacoes de classes de erro externas, nao podendo
 *  tratar o erro de forma consistente internamente
 *
 *      - Com uma hierarquia baseda em sealed classes, o autor da biblioteca
 *      pode se certificar que conhece todos os possíveis tipos de erros
 *
 *      - sealed class sao abstratas e seus contrutores podem ter como modificadores
 *      de acesso, protected (default) ou private
 */


sealed interface Error

sealed class IOError(): Error

class FileReadError(val pathfile: String): IOError()

object RuntimeError: Error


fun main() {

}