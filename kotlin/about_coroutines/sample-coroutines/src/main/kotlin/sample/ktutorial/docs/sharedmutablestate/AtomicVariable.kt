package sample.ktutorial.docs.sharedmutablestate

import java.util.concurrent.atomic.AtomicReference

/*
    https://www.baeldung.com/java-atomic-variables
 */

data class MutablePoint2D(var x: Int, var y: Int)

private fun checkAtomicReference() {
    /**
     *  https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/atomic/package-summary.html
     */

    val ref = AtomicReference<MutablePoint2D>()

    ref.set(MutablePoint2D(1, 2))

}

fun main() {

}