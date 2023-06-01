package com.br.tutorials.stackoverflow

import com.br.tutorials.model.User
import java.nio.charset.Charset
import java.security.MessageDigest


/**
 * collision possiblity of 16-char and 32-char md5
 * https://stackoverflow.com/questions/62038409/collision-possiblity-of-16-char-and-32-char-md5
 */

private val md5 = MessageDigest.getInstance("MD5")


private fun checkMd5() {

    fun test(user: User) {
        md5.update(user.toString().toByteArray())
        println(
            "HashCode: ${user.hashCode()}, HashMD5: ${md5.digest()}, ToString: ${user.toString()}"
        )
    }

    repeat(12) {
        test(User("chris", 123))
        println("****************************************")
    }

}


fun main() {
    checkMd5()
}