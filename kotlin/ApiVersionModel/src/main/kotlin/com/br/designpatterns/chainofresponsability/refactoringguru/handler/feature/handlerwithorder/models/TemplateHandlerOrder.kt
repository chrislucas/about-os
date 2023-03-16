package com.br.designpatterns.chainofresponsability.refactoringguru.handler.feature.handlerwithorder.models

abstract class TemplateHandlerOrder(private val nextHandle: HandlerOrder?) : HandlerOrder {

    abstract val levelTask: LevelTask

    abstract fun canIHandler(task: Task): Boolean

    override fun handler(task: Task): HandlerOrder? {
        return if (canIHandler(task)) {
            handlerTask(task)
            null
        } else {
            nextHandle
        }
    }
}