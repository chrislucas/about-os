package sample.serialization

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Serializable
data class Item(private val id: Long, private val description: String)

/**
 * inline class nao sao serializaveis. estudar sobre isso
 * */

@Serializable
data class OrderItem(private val item: Item, private val quantity: Long)

@Serializable
data class Order(private val items: List<OrderItem>)

fun serializerOrder(items: List<OrderItem>) = Json.encodeToString(Order(items))

fun main() {

    val list = listOf(OrderItem(Item(10, "batata chips"), 5)
        , OrderItem(Item(123, "biscoito"), 5)
        , OrderItem(Item(12, "papel higienico"), 15)
        , OrderItem(Item(321, "sabonete"), 1500)
    )

    println(serializerOrder(list))

}