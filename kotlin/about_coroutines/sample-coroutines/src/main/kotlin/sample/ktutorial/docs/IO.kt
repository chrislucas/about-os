package sample.ktutorial.docs

fun logCoroutineScope(message: String) = println(String.format("[%s]: %s",Thread.currentThread().name, message))