package com.br.designpatterns.chainofresponsability.refactoringguru.handler.feature.handlerwithorder.models


class ConcreteHandlerOrder {
    fun handler(task: Task): Boolean {
        val handlersChain: HandlerOrder = Enginner(TechLead(Manager(null)))
        var next = handlersChain.handler(task)
        while (next != null) {
            next = next.handler(task)
        }
        return haveAnyHandler(handlersChain)
    }

    private fun haveAnyHandler(handlersChain: HandlerOrder): Boolean {
        var next: HandlerOrder? = handlersChain
        var was = next?.wasHandled() ?: false
        next = handlersChain?.next()
        while (next != null && !was) {
            was = next.wasHandled()
            next = next.next()
        }
        return was
    }
}