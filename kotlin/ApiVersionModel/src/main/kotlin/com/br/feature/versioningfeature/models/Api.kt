package com.br.feature.versioningfeature.models

sealed class Api(val metada: Metada) {
    class Cart(metada: Metada) : Api(metada)
    class Seller(metada: Metada) : Api(metada)
    class Checkout(metada: Metada) : Api(metada)
    class Menu(metada: Metada) : Api(metada)
    class User(metada: Metada) : Api(metada)
}

data class Metada(val description: String, val version: String, val enabled: Boolean)
