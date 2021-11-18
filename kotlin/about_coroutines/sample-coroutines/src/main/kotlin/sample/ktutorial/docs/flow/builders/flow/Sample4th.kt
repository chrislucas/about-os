package sample.ktutorial.docs.flow.builders.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/*
    https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/flow.html

    a funcao
    fun <T> flow(block: suspend FlowCollector<T>.() -> Unit): Flow<T>

    Cria um Cold Flow a partir de uma suspend function passada por argumento
 */


private fun forceError() = runBlocking {

    flow {
        emit(1)
        withContext(Dispatchers.IO) {
            emit(2)
        }

        withContext(Dispatchers.Unconfined) {
            emit(4)
        }

        withContext(Dispatchers.Unconfined) {
            emit(8)
        }
    }
}

/*
    https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/flow-on.html
 */

private fun checkFlowOn() = runBlocking {

}



fun main() {
   runBlocking {
       //checkFlowOn().collect { println(it) }
       //forceError().collect { println(it) }
   }
}