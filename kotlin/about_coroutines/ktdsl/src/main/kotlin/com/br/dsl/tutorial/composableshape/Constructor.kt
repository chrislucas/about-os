package com.br.dsl.tutorial.composableshape

import com.br.dsl.tutorial.composableshape.BinaryCompoundShaped.*
import com.br.dsl.tutorial.composableshape.Square.MutableSquare
import com.br.dsl.tutorial.composableshape.Triangle.*


internal fun layout(fn: Canvas.() -> Unit): Canvas =
    with(Canvas()) {
        fn()
        this
    }

internal fun Canvas.square(create: MutableSquare.() -> Unit)  {
    this += MutableSquare().also(create).toSquare()
}

internal fun Canvas.triagle(create: MutableTriangle.() -> Unit) {
    this += MutableTriangle().also(create).toTriangle()
}

internal fun Canvas.space() {
    this += Space()
}

internal fun Canvas.compoundShape(create: MutableBinaryCompoundShaped.() -> Unit) {
    this += MutableBinaryCompoundShaped().also(create).applyOperation()
}