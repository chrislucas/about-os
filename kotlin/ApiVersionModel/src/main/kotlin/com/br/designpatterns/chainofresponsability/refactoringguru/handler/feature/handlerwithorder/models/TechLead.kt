package com.br.designpatterns.chainofresponsability.refactoringguru.handler.feature.handlerwithorder.models

class TechLead(private val next: HandlerOrder?) : TemplateHandlerOrder(next) {
    override val levelTask: LevelTask
        get() = LevelTask.Senior

    private var wasHandled: Boolean = false


    override fun handlerTask(task: Task) {
        println("Techlead Processing: $task")
        wasHandled = true
    }

    override fun wasHandled(): Boolean = wasHandled

    override fun next(): HandlerOrder? = next

    override fun canIHandler(task: Task): Boolean = levelTask == task.levelTask
}