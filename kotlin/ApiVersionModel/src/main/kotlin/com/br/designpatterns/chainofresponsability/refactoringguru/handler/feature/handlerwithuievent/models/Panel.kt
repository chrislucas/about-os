package com.br.designpatterns.chainofresponsability.refactoringguru.handler.feature.handlerwithuievent.models


/*
    Concrete handler
 */
class Panel(
    override val label: String,
    override val toolTipText: String? = null,
    container: Container? = null
) : Container(label, container) {

    override fun showTooltip(): String {
        return if (toolTipText != null) {
            "I'm $label The Painel. Tip: $toolTipText"
        } else {
            super.showTooltip()
        }
    }

}