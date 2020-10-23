package sample.serialization

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json

/**
 * https://github.com/Kotlin/kotlinx.serialization#using-apply-plugin-the-old-way
 * https://blog.jetbrains.com/kotlin/2020/10/kotlinx-serialization-1-0-released/
 * */

@Serializable
data class ModelSample(private val id: Long, private val description: String)


class SerializationStrategyModelSample(override val descriptor: SerialDescriptor) : SerializationStrategy<ModelSample> {

    override fun serialize(encoder: Encoder, value: ModelSample) {}
}


@Serializable
object BitOperation {
    private const val FLAG = 0x01

    fun on(n: Int, pos: Int): Int {
        return if (pos <= 0) {
            return n
        } else {
            n or (FLAG shl (pos - FLAG))
        }
    }

    fun off(n: Int, pos: Int): Int {
        return if (pos <= 0) {
            n
        } else {
            n and (FLAG shl (pos - FLAG)).inv()
        }
    }
}

fun serializationTest() {
    val model = ModelSample(
        1, "I'm learning the new Serializable Kotlin's library"
    )

    // function da Interface StringFormat
    // public fun <T> encodeToString(serializer: SerializationStrategy<T>, value: T): String
    //Json.encodeToString()

    // extension function
    println(Json.encodeToString(model))

    val instanceOfModelSample = Json.decodeFromString<ModelSample>("{\"id\":2, \"description\": \"just kidding\"}")

    println(instanceOfModelSample)

    // nao funciona mesmo "escapando as aspas"
    //println(Json.decodeFromString<ModelSample>("{\'id\':3, \'description\': \'another description\'}"))

    println(Json.encodeToString(BitOperation))
}

/**
 * https://blog.jetbrains.com/kotlin/2020/10/kotlinx-serialization-1-0-released/
 *
 * */
fun serializationListModel() {

    val serialized = Json.encodeToString(listOf(ModelSample(1, "chris")
        , ModelSample(2, "lucas")))

    println(serialized)

    println(Json.decodeFromString<List<ModelSample>>(serialized))
}

fun main() {
    serializationListModel()
}