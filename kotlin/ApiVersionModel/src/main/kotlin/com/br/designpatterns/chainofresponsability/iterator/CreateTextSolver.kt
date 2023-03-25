package com.br.designpatterns.chainofresponsability.iterator

data class Context(val text: String)

data class Result(val nextSolver: TemplateSolver?, val wasSolved: Boolean)

abstract class TemplateSolver(open val next: TemplateSolver? = null) {

    abstract fun shouldProcess(context: Context): Boolean

    // findText
    abstract fun process(context: Context): Unit

    fun solver(context: Context): Result {
        return if (shouldProcess(context)) {
            process(context)
            Result(this, true)
        } else {
            Result(next, false)
        }
    }
}


class SolverEmptyContextText(override val next: TemplateSolver? = null) : TemplateSolver(next) {

    override fun shouldProcess(context: Context): Boolean {
        return context.text.isEmpty()
    }

    override fun process(context: Context) {
        print("Resulvido: context is empty")
    }
}

class SolverNonEmptyContextText(override val next: TemplateSolver? = null) : TemplateSolver(next) {

    override fun shouldProcess(context: Context): Boolean {
        return context.text.isNotEmpty()
    }

    override fun process(context: Context) {
        print("Resolvido: is not empty")
    }
}

class SolverContextTextEqualsBarcelona(override val next: TemplateSolver? = null): TemplateSolver(next) {
    override fun shouldProcess(context: Context): Boolean {
        return context.text == "Barcelona"
    }

    override fun process(context: Context) {
        print("Resolvido: String == Barcelona")
    }
}

class SolverTextWithRegex(val regex: Regex, override val next: TemplateSolver? = null) : TemplateSolver(next) {
    override fun shouldProcess(context: Context): Boolean {
        return regex.matches(context.text)
    }

    override fun process(context: Context) {
        print("Resolvido com regex")
    }

}


private fun check1() {
    val solvers = SolverEmptyContextText(
        SolverNonEmptyContextText(
            SolverTextWithRegex(Regex("^/w/[0-9]$"))
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

    val c = SolverNonEmptyContextText()
    val b = SolverTextWithRegex(Regex("^\\w[0-9]$"), c)
    val a = SolverEmptyContextText(b)

    val context = Context("teste nao vazio")
    var result = a.solver(context)
    while (result.nextSolver != null) {
        result = result.nextSolver?.solver(context) ?: Result(null, false)
    }

    println(result)
}

private fun check3() {

    //val solverNonEmpty = SolverNonEmptyContextText()
    val solverContextStringEqBarcelona = SolverContextTextEqualsBarcelona()
    val solverWithRegex = SolverTextWithRegex(Regex("^\\w[0-9]$"), solverContextStringEqBarcelona)
    val solverEmpty = SolverEmptyContextText(solverWithRegex)


    listOf("w3", " ", "").forEach { text ->
        val context = Context(text)
        var (nextSolver, wasSolved) = solverEmpty.solver(context)

        while (nextSolver != null && !wasSolved) {
            val result = nextSolver.solver(context)
            nextSolver = result.nextSolver
            wasSolved = result.wasSolved
        }

        print(if (wasSolved) " - solved\n"  else "[$context] - not solved\n")
    }
}


fun main() {
    check3()
}