package com.br.designpatterns.chainofresponsability.refactoringguru.handler.feature.handlerwithorder.models

class Manager(private val next: HandlerOrder?) : TemplateHandlerOrder(next) {
    override val levelTask: LevelTask
        get() = LevelTask.Manager

    private var wasHandled: Boolean = false

    override fun handlerTask(task: Task) {
        println("Manager Processing: $task")
        wasHandled = true
    }

    override fun wasHandled(): Boolean = wasHandled

    override fun next(): HandlerOrder? = next

    override fun canIHandler(task: Task): Boolean = levelTask == task.levelTask
}