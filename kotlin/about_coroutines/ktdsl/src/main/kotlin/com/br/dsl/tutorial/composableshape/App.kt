package com.br.dsl.tutorial.composableshape

private fun createLayout() {
    layout {
        this += square {
            this.x = 0
            this.y = 0
            this.pos = Pair(0, 0)
        }
    }.show()
}

fun main() {
    createLayout()
}