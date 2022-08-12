package sample.tutorials.micronautsutils.logging

import io.klogging.java.LoggerFactory
import io.klogging.logger
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging

/*
    https://www.baeldung.com/kotlin/kotlin-logging-library
    Kotlin-logging is a slf4j wrapper

 */

private val logger = KotlinLogging.logger {
    println("NOTHING")
}


val eval: (String) -> String = {
    println("LogEval: $it")
    "Value Returned"
}

private fun checkTraceFunction() {
    /*
        A chamada a funcao trace com parenteses nao vai logar nada assim como a trace {}
        porem ela vai executar a funcao eval e executa a funcao println.
        Entao, se quisermos que trace tenha alguma saida mesmo sem configurarmos no arquivo logback.xml
        devemos usar o metodo com parenteses
     */
    logger.trace("Running Trace Parentheses: ${eval("eagerly")}")
    logger.trace { "Running Trace Curly Braces: ${eval("Lazily")}" }
}

private fun checkDebugFunction() {
    logger.debug("Running Trace Parentheses: ${eval("eagerly")}")
    logger.debug { "Running Trace Curly Braces: ${eval("Lazily")}" }
}

private fun checkFunctions() {
    logger.trace { "Trace Log" }
    logger.debug { "Debug Log" }
    logger.info { "Info Log" }
    logger.warn { "Warn Log" }
    logger.error { "Error Log" }
}


private suspend fun checkNamedLogger() {
    logger("Named Logger Info").info { "Info Log" }
    logger("Named Logger Warn").warn { "Warn Log" }
}

private fun checkGetNamedLog() {
    val namedLogger = KotlinLogging.logger("WHAT_EVER") //LoggerFactory.getLogger("WHAT_EVER")
    namedLogger.debug { "Debug NamedLog" }
}

fun main() {
    checkTraceFunction()
    //checkDebugFunction()
    runBlocking {
        // checkNamedLogger()
    }

    checkGetNamedLog()
}