package sample.ktutorial.docs.flow

import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import java.util.*

private fun streaming() = flow {
    var acc = 0L
    while (acc < Long.MAX_VALUE) {
        kotlinx.coroutines.delay(1000L)
        emit(System.currentTimeMillis())
        acc += 1
    }
}


private fun listening() = runBlocking {
    val date = Date(Calendar.getInstance().timeInMillis)
    streaming()
        .cancellable()
        .collect {
            date.time = it
            println(date)
        }
}


fun main() {
    listening()
}