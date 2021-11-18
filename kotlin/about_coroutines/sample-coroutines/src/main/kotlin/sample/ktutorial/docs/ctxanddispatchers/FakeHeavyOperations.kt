package sample.ktutorial.docs.ctxanddispatchers

fun fakeOperationWithStringConcat(): String {
    var c = 100000000
    val buffer = StringBuilder()
    while (c > 0) {
        buffer.append(c)
        c -= 1
    }
    return buffer.toString()
}