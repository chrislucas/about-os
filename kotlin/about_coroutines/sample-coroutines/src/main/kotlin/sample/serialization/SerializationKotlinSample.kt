package sample.serialization

import kotlinx.serialization.Serializable


/**
 * https://github.com/Kotlin/kotlinx.serialization#using-apply-plugin-the-old-way
 * https://blog.jetbrains.com/kotlin/2020/10/kotlinx-serialization-1-0-released/
 * */

@Serializable
data class ModelSample(private val id: Long, private val description: String)

fun main() {

}