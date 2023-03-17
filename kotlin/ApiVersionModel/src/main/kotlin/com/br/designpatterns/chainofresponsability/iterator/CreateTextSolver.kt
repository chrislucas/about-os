package com.br.designpatterns.chainofresponsability.iterator

class Context(val text: String)

class Result(val nextSolver: TemplateSolver?, wasSolved: Boolean)

abstract class TemplateSolver(open val next: TemplateSolver? = null) {

    abstract fun shouldIResolverIt(context: Context): Boolean

    abstract fun execute(context: Context): Unit

    fun solver(context: Context): Result {
        return if (shouldIResolverIt(context)) {
            execute(context)
            Result(this, true)
        } else {
            Result(next, false)
        }
    }
}


class SolverEmptyContextText(override val next: TemplateSolver? = null) : TemplateSolver(next) {

    override fun shouldIResolverIt(context: Context): Boolean {
        return context.text.isEmpty()
    }

    override fun execute(context: Context) {
        println("Resulvido: context is empty")
    }

}

class SolverNonEmptyContextText(override val next: TemplateSolver? = null) : TemplateSolver(next) {

    override fun shouldIResolverIt(context: Context): Boolean {
        return context.text.isNotEmpty()
    }

    override fun execute(context: Context) {
        println("Resolvido: is not empty")
    }
}

class SolverTextWithReges(val regex: Regex, override val next: TemplateSolver? = null): TemplateSolver(next) {
    override fun shouldIResolverIt(context: Context): Boolean {
        return regex.matches(context.text)
    }

    override fun execute(context: Context) {
        println("Resolvido com regex")
    }

}


private fun check1() {
    val solvers = SolverEmptyContextText(
        SolverNonEmptyContextText(
            SolverTextWithReges(Regex("^/w/[0-9]$"))
        )
    )
    listOf(Context(""), Context("a2"), Context("teste nao vazio")).forEach {
        var result = solvers.solver(it)
        while (result.nextSolver != null) {
            result = result.nextSolver?.solver(it) ?: Result(null, false)
        }
    }
}

private fun check2() {
    val solvers = SolverEmptyContextText(
        SolverTextWithReges(Regex("^\\w[0-9]$"),
            SolverNonEmptyContextText()
        )
    )
    listOf(
        Context(""),
        Context("a2"),
        Context("teste nao vazio")
    ).forEach {
        var result = solvers.solver(it)
        while (result.nextSolver != null) {
            result = result.nextSolver?.solver(it) ?: Result(null, false)
        }
    }
}


fun main() {
    check2()
}