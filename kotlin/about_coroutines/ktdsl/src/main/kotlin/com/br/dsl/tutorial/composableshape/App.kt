package com.br.dsl.tutorial.composableshape

private fun createLayout() {
    layout {
        square {
            this.x = 0
            this.y = 0
            this.pos = Pair(0, 0)
        }

        space()

        compoundShape {
            p = Square()
            q = Triangle()
            operation = ShapeOperation.UNION
            pos = Pair(1, 1)
        }

        space()

        triagle {
            p = Pair(0, 0)
            q = Pair(0, 0)
            r = Pair(0, 0)
            center = Pair(0, 0)
        }

        space()

    }.show()
}

/**
 * Artigo sobre a cricao de uma DSL em kotlin
 * https://medium.com/kotlin-en-android/kotlin-dsl-introduccion-f112557f5662
 * https://medium.com/kotlin-en-android/kotlin-dsl-codigo-base-6536948beb93
 * https://medium.com/kotlin-en-android/kotlin-dsl-construccion-del-dsl-1-893eaf900cc
 *
 * Projeto do artigo
 * https://github.com/pencelab/Shapes-DSL/tree/master
 */
fun main() {
    createLayout()
}