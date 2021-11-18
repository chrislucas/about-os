package sample.ktutorial.docs.coroutinescope

import kotlinx.coroutines.*

/*
    https://s3-sa-east-1.amazonaws.com/thedevconf/presentations/TDC2018POA/androidkotlin/VPD-2346_2018-12-05T040336_Programa%C3%A7%C3%A3o%20Ass%C3%ADncrona%20com%20Kotlin%20Coroutines.pdf
 */

data class Data(val tag: String)

suspend fun getData(): Deferred<List<Data>> =

    /*
         function  coroutineScope
         Segundo a doc

         This function is designed for parallel decomposition of work. When any child coroutine in this scope fails,
         this scope fails and all the rest of the children are cancelled (for a different behavior see supervisorScope).
         This function returns as soon as the given block and all its children coroutines are completed.
     */
    coroutineScope {

        val values = withContext(Dispatchers.IO) {
            async { mockRequestData() }
        }


        val valuesProcessed = withContext(Dispatchers.IO) {
            async {
                values.await().map { data ->
                    if (data.tag == "1000") {
                        this.cancel(CancellationException("Mock Error: $this"))
                    }
                    Data("new#${data.tag}")
                }
            }
        }

        valuesProcessed
    }


suspend fun mockRequestData(): List<Data> = (1..100000).map { Data("$it") }


fun main() {
    // Run new coroitine and blocks current Thread until its completion
    runBlocking {
        println(getData().await())
    }
}