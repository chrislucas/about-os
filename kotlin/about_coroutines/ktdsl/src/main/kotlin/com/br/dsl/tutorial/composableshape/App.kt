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

fun main() {
    createLayout()
}