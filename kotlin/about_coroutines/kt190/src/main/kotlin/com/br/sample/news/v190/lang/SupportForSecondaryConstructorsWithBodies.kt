package com.br.sample.news.v190.lang

/*
    https://kotlinlang.org/docs/whatsnew19.html#support-for-secondary-constructors-with-bodies-in-inline-value-classes
    Foi estabilizado no kotlin 1.9.0 o uso de contrutores secundarios com corpo em inline value class
        - https://kotlinlang.org/docs/inline-classes.html
 */

@JvmInline
value class User(private val nickname: String) {
    init {
        check(nickname.isNotEmpty()) { "Is blank or empty" }

        println("Init")
        println("Is blank ? ${nickname.isBlank() }")
        println("Is empty ? ${nickname.isEmpty() }")
    }

    constructor(name: String, nickname: String): this(nickname) {
        check(name.isNotEmpty() && nickname.isNotBlank()) { "name is empty" }
    }

    override fun toString() = "$nickname"
}

private fun checkUser() {
    //User("")
    User("", "user123")
}

fun main() {
    checkUser()
}