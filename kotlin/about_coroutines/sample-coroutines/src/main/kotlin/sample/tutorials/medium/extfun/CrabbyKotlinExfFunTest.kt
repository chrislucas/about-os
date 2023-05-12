package sample.tutorials.medium.extfun

/**
 * https://medium.com/codex/crabby-kotlin-what-is-7bc04d678d1b
 */


interface Command {
    fun execute()
}


class CommandImpl : Command {
    override fun execute() {
        println("Just, say hello [$this]")
    }

    private fun notAcessibleByInstance() {
        println("internal")
    }

    fun invoke(fn: Command.() -> Unit) {
        fn()
    }
}

private fun Command.outOfBox() {
    println("Just say hello out of box [$this]")
}


private fun testExtensionFunction() {
    val command = CommandImpl()

    command.invoke { execute() }

    command.invoke { outOfBox() }

    command.invoke {
        // Essa funcao nao é vista por uma instancia
        // notAcessibleByInstance()

        println("Criando extensoes em tempo de execucao [$this]")
    }
}

fun main() {
    testExtensionFunction()
}