package com.br.designpatterns.chainofresponsability.refactoringguru.handler.feature.handlerwithorder.models

interface HandlerOrder {
    fun handler(task: Task): HandlerOrder?
    fun handlerTask(task: Task)
    fun wasHandled(): Boolean
    fun next(): HandlerOrder?
}