package sample.ktutorial.ktacademy.tutorial.mutability


/*
    https://kt.academy/article/ek-mutability
 */



private fun check() {
    val a = mutableListOf(2)
    var b = listOf(2)
    a += 2
    b = b + 2 //cria uma nova lista
    //b = 2 + b//cria uma nova lista
}

fun main() {

}