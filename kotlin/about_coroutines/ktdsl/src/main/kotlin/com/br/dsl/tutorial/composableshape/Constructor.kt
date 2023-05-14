package com.br.dsl.tutorial.composableshape

import com.br.dsl.tutorial.composableshape.Square.MutableSquare

internal fun layout(fn: Canvas.() -> Unit): Canvas =
    with(Canvas()) {
        fn()
        this
    }

internal fun square(create: MutableSquare.() -> Unit): Square =
    with(MutableSquare()) {
        create()
        this
    }.toSquare()