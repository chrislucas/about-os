package com.br.designpatterns.chainofresponsability.refactoringguru.handler.feature.handlerwithuievent.models

/*
    Concrete handler
 */
class Button(
    override val label: String,
    override val toolTipText: String? = null,
    override val container: Container? = null
) : Component(label, toolTipText, container) {

    // Vamos deixar com que esse componente utilize a solucao da sua classe Mae
    // override fun showTooltip(): String = "I''m $label, the button"

}