package sample.ktutorial.basics

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// https://kotlinlang.org/docs/coroutines-basics.html#extract-function-refactoring

suspend fun show(message: String, mills: Long) {
    delay(mills)
    print(message)
}


fun main() {
    runBlocking {
        launch {
            show(" Chris Lucas ",  1000L)
        }
        print("Ola,")
    }
    println("Teste 0xff")
}



