package sample.serialization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Polymorphic serialization: https://blog.jetbrains.com/kotlin/2020/10/kotlinx-serialization-1-0-released/
 * */

@Serializable
data class SystemUser(@SerialName("sys_level") private val level: SystemLevel
                      , @SerialName("username") private val name: String)

@Serializable
sealed class SystemLevel

@Serializable
object Admin : SystemLevel()

@Serializable
object Operator : SystemLevel()

@Serializable
object Guest : SystemLevel()

fun sampleListOfSystemUsersSerialized() {
    val users = listOf(SystemUser(Admin, "chris")
        , SystemUser(Operator, "lucas")
        , SystemUser(Guest, "andre")
    )
    println(Json.encodeToString(users))
}


@Serializable
sealed class CypherAlgorithm {
    abstract val content: String
}

@Serializable
class RSA(private val _content: String) : CypherAlgorithm() {
     override val content: String
        get() {
            return _content
        }
}

@Serializable
class DSA(private val _content: String): CypherAlgorithm() {
    override val content: String
        get() {
            // apply algorithm
            return content
        }
}

@Serializable
class AES(private val _content: String): CypherAlgorithm() {
    override val content: String
        get() {
            return _content
        }
}

fun sampleListOfCypherAlgorithm() {
    val content = "chrisluccas"
    val algorithms = listOf(RSA(content), DSA(content), AES(content))
    val contentJson = Json.encodeToString(algorithms)
    println(contentJson)
    println(Json.decodeFromString<List<CypherAlgorithm>>(contentJson))
}

fun sampleDataClassWithSerialName() {
    // {"sys_level":{"type":"sample.serialization.Admin"},"username":"chrisluccas"}
    println(Json.encodeToString(SystemUser(Admin, "chrisluccas")))
    val userJson = "{\"sys_level\":{\"type\":\"sample.serialization.Admin\"}" +
            ",\"username\":\"chrisluccas\"}"
    println(Json.decodeFromString<SystemUser>(userJson))
}

fun main() {
    sampleDataClassWithSerialName()
}