package com.br.designpatterns.chainofresponsability.refactoringguru.handler.feature.handlerwithorder

import com.br.designpatterns.chainofresponsability.refactoringguru.handler.feature.handlerwithorder.models.ConcreteHandlerOrder
import com.br.designpatterns.chainofresponsability.refactoringguru.handler.feature.handlerwithorder.models.Task
import com.br.designpatterns.chainofresponsability.refactoringguru.handler.feature.handlerwithorder.models.LevelTask

/*
    Estrutura baseada no artigo
    https://refactoring.guru/pt-br/design-patterns/chain-of-responsibility
 */

fun main() {
    val tasks = listOf(
        Task("Criar uma atividade no Jira", LevelTask.Beginner),
        Task("Documentar a solução Arquitetural de uma API", LevelTask.Senior),
        Task("Destribuit os Engenheiros em Times", LevelTask.Manager),
        Task("Criar Documentação do projeto", LevelTask.Senior),
        Task("Definir estratégia anual", LevelTask.Director),
    )
    val concreteHandlerOrder = ConcreteHandlerOrder()
    tasks.forEach { order ->
        println("Ordem: $order Teve algum tratamento ? ${concreteHandlerOrder.handler(order)}.\n")
    }
}