package com.br.designpatterns.chainofresponsability.refactoringguru.handler.feature.handlerwithuievent.models


/*
    Abstract handler
 */
abstract class Component(
    open val label: String,
    open val toolTipText: String? = null,
    open val container: Container? = null
) : ToolTipContext {

    override fun showTooltip(): String {
        return toolTipText ?: callNext()
    }

    private fun callNext() = container?.showTooltip() ?: "$this, does not have a container"
}