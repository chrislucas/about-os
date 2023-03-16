package sample.ktutorial.atomictypes

import java.util.concurrent.atomic.AtomicInteger

class SequenceAtomicInt(private val init: Int = 0) {
    private val atomicInteger = AtomicInteger(init)
    fun next() = atomicInteger.incrementAndGet()
}