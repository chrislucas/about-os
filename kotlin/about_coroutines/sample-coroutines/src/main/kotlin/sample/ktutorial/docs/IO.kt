package sample.ktutorial.docs

fun logCoroutineScope(message: String) = Thread.currentThread().run {
    println(
        String.format(
            "MSG: %s\nName: [%s]\nThreadGroup [%s]\n", message, name, threadGroup.name
        )
    )
}