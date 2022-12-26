package sample.ktutorial.ktacademy.tutorial.coroutines.t1

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


/*
    https://kt.academy/article/cc-constructing-scope
 */

class CustomCoroutineScopeImpl(override val coroutineContext: CoroutineContext = Job()) : CoroutineScope {

    fun onStart() {
        launch {  }
    }
}

private fun checkCustomCoroutineScope() {
    val customCoroutineScope =CustomCoroutineScopeImpl()
    customCoroutineScope.cancel()
}



/*
    Essa abordagem e apontada como mais segura por nao ter acesso direto a CoroutineScope
    e por consequencia nao conseguir cancela atraves de uma instancia de WrapperCoroutineScope
    de forma acidental. Nao temos acesso a metodos como cancel, ou enrureActive
 */
class WrapperCoroutineScope(private val scope: CoroutineScope) {

    // Ou ate mesmo tornar scope um atributo criado pela propria classe
    // o que a deixaria menos flexível, mas pode ter alguma utilidade
    private val anotherScope = CoroutineScope(Dispatchers.Default)

    fun onStart() {
        scope.launch {  }
    }

}

private fun checkWrapperCoroutineScope() {
    val wrapperCoroutineScope = WrapperCoroutineScope(CoroutineScope(Dispatchers.IO))
    wrapperCoroutineScope.onStart()
}

/**
    A forma mais simples de se criar uma instancia de CoroutineScope e usando uma factory function
    da propria linguagem.
    * @see CoroutineScope
     *
     * No android
     - Projetos android utilizam geralmente ou MVVM ou MVP, criando a logica de apresentacao
 em objetos chamados ViewModels ou Presenters respectivamente.



 */




fun main() {

}