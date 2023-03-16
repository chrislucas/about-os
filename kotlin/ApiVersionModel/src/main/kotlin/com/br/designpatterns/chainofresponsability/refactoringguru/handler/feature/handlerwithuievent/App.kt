package com.br.designpatterns.chainofresponsability.refactoringguru.handler.feature.handlerwithuievent

import com.br.designpatterns.chainofresponsability.refactoringguru.handler.feature.handlerwithuievent.models.Button
import com.br.designpatterns.chainofresponsability.refactoringguru.handler.feature.handlerwithuievent.models.Dialog
import com.br.designpatterns.chainofresponsability.refactoringguru.handler.feature.handlerwithuievent.models.Panel


/*
    Implementacao do exemplo do site
    https://refactoring.guru/pt-br/design-patterns/chain-of-responsibility
 */


private fun checkCase1() {
    val dialog = Dialog("Dialog #1", "Tooltiptext Dialog")
    val panel = Panel("Panel #1", "Tooltip Panel")
    val buttonOk = Button("Ok", "Simple Ok Button")
    val buttonCancel = Button("Cancel", "Simple Cancel Button")
    val button1 = Button("Button 1", container = panel)

    panel += buttonOk
    panel += buttonCancel
    panel += button1
    dialog += panel

    println(dialog.showTooltip())
    println(button1.showTooltip())
    println(panel.showTooltip())
}

private fun checkCase2() {
    val dialog = Dialog("Dialog #1", "Tooltiptext Dialog")
    val panel = Panel("Panel #1", container = dialog)
}

fun main() {

}