package com.br.designpatterns.chainofresponsability.refactoringguru.handler.feature.handlerwithuievent.models


abstract class Container(
    override val label: String,
    override val container: Container? = null
) : Component(label, container = container) {

    private val children = mutableListOf<Component>()

    operator fun plusAssign(child: Component) {
        children += child
    }
}