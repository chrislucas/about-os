package sample.ktutorial.ktacademy.tutorial

private val name: String? = "chris"
private val surname: String = "luccas"


/*
    Smart casting aqui nao eh possivel pois usamos um getter
 */
private val fullname: String?
    get() = name?.let { "$it $surname" }


/*
    Non-local properties can be smart-casted only when they are final and do not have a custom getter
 */
private val fullname2: String? = name?.let { "$it $surname" }

private fun checkSmartCasting() {
    if (fullname != null) {
        // aqui nao vai ocorrer smart casting, o compilado nao vai entender que por
        // passar pelo if a variavel fullname nao eh nula
        println(fullname?.length)
    }

    if (fullname2 != null) {
        // aqui o smart casting funciona LEGAL
        println(fullname2.length)
    }
}


fun main() {
    checkSmartCasting()
}

