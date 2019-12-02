package samples.kotlin

/**
 * Estudando o link? https://kotlinlang.org/docs/reference/object-declarations.html
 * */

/**
 * uma funcao pode retornar um objeto anonumo
 * "Um objeto anonimo pode ser usado como tipo somente em declaracoes privadas e locais. Se usarmos um objeto
 * como retornno de uma funcao publica ou como tipo de uma propriedade publica o valor real da funcao ou propriedade
 * sera declarado como um supertipo do objeto anonimo ou um tipo 'Any' (Object do kotlin) se eu nao declarar nenhum supertipo, assim
 * os membros declarados dentro de objetos sem um supertipo nao poderao ser acessados"
 *
 * */
private fun create2DPointRep(x: Int, y: Int) = object {
    val x = x
    val y = y
    override fun toString() : String = "[Point2D: ($x, $y)]"
}

/*
* a funcao abaixo retorna um objeto do tipo 'Any' ja que nao foi declarado um supertipo
* */
fun create3DPointerRep(x: Int, y: Int, z: Int) = object {
    val x = x
    val y = y
    val z = z
    override fun toString() : String = "[Point3D: ($x, $y, $z)]"
}

// Uma funcao que retorna um objeto cujo supertipo foi definido
fun myThread(exec: () -> Unit)  = object : Thread({exec()}) {}

/**
 * Observacao, objetos sao singleton
 * */
private val Plane = object {
    var width = 0
    var height = 0
    override fun toString(): String {
        return "w:$width, h:$height]"
    }
}

fun testPrivateProperty() {
    val plane1 = Plane
    plane1.apply {
        this.width = 100
        this.height = 100
    }

    val plane2 = Plane
    plane2.apply {
        this.width = 200
        this.height = 200
    }

    // Objetos sao Singletons
    println(plane1)
    println(plane2)
}

fun testPublicFunctionReturnTypedObjectAnon() {
    val myThread = myThread {
        println("Oloko")
    }

    myThread.start()
}

fun main() {
    val p2D1 = create2DPointRep(10, 2)
    p2D1.apply { println("(${this.x}, ${this.y}) ") }

    create3DPointerRep(10,20,30)
    // val p3D1 nao tem acesso ao atributo x porque a funcao create3DPointerRep(x,y,z) retornar "Any"
    // isso ocorre porque ela eh publica
    // p3D1.x
    testPrivateProperty()
    testPublicFunctionReturnTypedObjectAnon()

}