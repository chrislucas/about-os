package com.br.designpatterns.chainofresponsability.refactoringguru.handler.feature.handlerwithuievent.models

/*
    Concrete handler
 */

class Dialog(
    override val label: String, override val toolTipText: String?,
    container: Container? = null
) : Container(label, container) {

    override fun showTooltip(): String {
        return if (toolTipText != null) {
            "I'm $label, the Dialog. Tip: $toolTipText"
        } else {
            super.showTooltip()
        }
    }
}