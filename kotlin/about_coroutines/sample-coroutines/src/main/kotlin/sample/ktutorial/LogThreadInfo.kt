package sample.ktutorial

fun logCoroutineScope(message: String) = println(String.format("[%s]: %s",Thread.currentThread().name, message))