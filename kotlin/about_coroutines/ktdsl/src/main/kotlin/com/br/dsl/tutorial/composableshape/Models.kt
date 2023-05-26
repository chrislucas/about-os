package com.br.dsl.tutorial.composableshape


/**
 * https://medium.com/kotlin-en-android/kotlin-dsl-introduccion-f112557f5662
 * DSL - domains specific language | GPL General Programming Language
 *
 * DSL Interna
 *  - Dedicada a realizar alguma tareda especifica dentro do conjunto de tarefas realizadas dentro duma aplicação
 *  - A DSL interna convive com o código da aplicação sem gerar nenhum conflito pois é desenvolvida na mesma linguagem
 *  que é utulizada na aplicação
 *      - um exemplo é a DSL da biblitoeca de injecao de dependencia koin
 *      - outro exemplo e a dsl da biblioteca ktor. Com essa dsl podemos escrever o trecho de codigo
 *      para iniciar um microservico
 *
 *  O autor desse artigo disponibilizou exemplos de como poderia ser uma DSl para resolver um problema. O exemplo
 *  dado trata de criar  e compor formas geométricas e pode ser visto aqui
 *  https://github.com/pencelab/Shapes-DSL/tree/master/src/main/kotlin/console_shapes
 */

/**
 * O codigo abaixo visa estudar o seguite repo
 * https://github.com/pencelab/Shapes-DSL/tree/master/src/main/kotlin/console_shapes
 *
 * Vou ler o codigo e tentar brincar e testar um pouco dele
 */

typealias PairInt = Pair<Int, Int>


internal class Canvas {
    private val shapes: MutableList<GeomShape> = mutableListOf()

    operator fun plusAssign(shape: GeomShape) {
        shapes += shape
    }

    fun show() {
        println("Shapes: $shapes")
    }
}

internal sealed class GeomShape(val pos: PairInt) {
    protected companion object {
        const val WHITE_SPACE = ' '
    }

    abstract infix fun interssection(q: GeomShape): GeomShape
    abstract infix fun union(q: GeomShape): GeomShape

    val grid: Array<CharArray> = arrayOf()

    protected open val line: (Int) -> CharArray
        get() = { idx ->
            when {
                idx in grid.indices -> {
                    grid[idx]
                }

                grid.isNotEmpty() -> {
                    CharArray(grid[0].size) { WHITE_SPACE }
                }

                else -> {
                    charArrayOf()
                }
            }
        }
}

internal open class Square(val x: Int = 0, val y: Int = 0, pos: PairInt = Pair(0, 0)) : GeomShape(pos) {

    override fun toString(): String {
        return "q"
    }

    class MutableSquare(
        var x: Int = 0,
        var y: Int = 0,
        var pos: PairInt = Pair(0, 0)
    ) {
        fun toSquare() = Square(x, y, pos)
    }

    override infix fun interssection(q: GeomShape): GeomShape = this
    override infix fun union(q: GeomShape): GeomShape = this
}

internal class Space(private val size: Int = 1) : Square(0, 0, Pair(0, 0)) {
    override val line: (Int) -> CharArray
        get() = {
            CharArray(size) { WHITE_SPACE }
        }

    override infix fun interssection(q: GeomShape): GeomShape = this
    override infix fun union(q: GeomShape): GeomShape = this

    override fun toString(): String {
        return "s"
    }
}

internal class Triangle(val p: PairInt, val q: PairInt, val r: PairInt, pos: PairInt) : GeomShape(pos) {
    constructor() : this(Pair(0, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0))

    override fun toString(): String {
        return "t"
    }

    override infix fun interssection(q: GeomShape): GeomShape = this
    override infix fun union(q: GeomShape): GeomShape = this

    class MutableTriangle(
        var p: PairInt = Pair(0, 0),
        var q: PairInt = Pair(0, 0),
        var r: PairInt = Pair(0, 0),
        var center: PairInt = Pair(0, 0)
    ) {
        fun toTriangle() = Triangle(p, q, r, center)
    }
}

internal enum class ShapeOperation {
    UNION, INTERSECTION
}

internal class BinaryCompoundShaped(
    val p: GeomShape,
    val q: GeomShape,
    val operation: ShapeOperation,
    pos: PairInt = Pair(0, 0)
) : GeomShape(pos) {
    override infix fun interssection(q: GeomShape): GeomShape = this

    override infix fun union(q: GeomShape): GeomShape = this

    class MutableBinaryCompoundShaped(
        var p: GeomShape = Space(),
        var q: GeomShape = Space(),
        var operation: ShapeOperation = ShapeOperation.UNION,
        var pos: PairInt = Pair(0, 0)
    ) {
        fun toBinaryCompoundShaped() = BinaryCompoundShaped(
            p, q, operation, pos
        )

        fun applyOperation(): GeomShape {
            return when (operation) {
                ShapeOperation.UNION -> {
                    Space()
                }

                else -> {
                    Space()
                }
            }
        }
    }

    override fun toString(): String {
        return "$p, $q"
    }
}
