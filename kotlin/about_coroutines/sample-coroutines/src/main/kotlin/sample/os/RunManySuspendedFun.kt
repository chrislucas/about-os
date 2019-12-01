package sample.os

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.thread

// https://kotlinlang.org/docs/tutorials/coroutines/coroutines-basic-jvm.html#lets-run-a-lot-of-them

fun stress() {
    val c = AtomicLong()
    for (i in 1 ..  1_000_000L) {
        thread(start = true) {
            c.addAndGet(i)
        }
    }
    println(c.get())
}


fun startAMillionCoroutines() {
    val c = AtomicLong()
    for (i in 1 .. 1_000_000L) {
       GlobalScope.launch {
           c.addAndGet(i)
       }
    }
    println(c.get())
}

fun main() {
    startAMillionCoroutines()
}