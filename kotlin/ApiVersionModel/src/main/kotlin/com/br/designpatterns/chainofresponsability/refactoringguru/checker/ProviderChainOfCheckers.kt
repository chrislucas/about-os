package com.br.designpatterns.chainofresponsability.refactoringguru.checker

class CheckEmailAttachment(val next: Checker? = null, private val content: String? = null) : TemplateChecker() {

    override fun canISolver(): Boolean = content != null

    override fun solver() {
        println("CheckEmailAttachment content: $content")
    }

    override fun next(): Checker? = next
}

class CheckCompressedBinaryFile(val next: Checker?, private  val content: String? = null): TemplateChecker() {
    override fun canISolver(): Boolean = content != null

    override fun solver() {
        println("CheckCompressedBinaryFile content: $content")
    }

    override fun next(): Checker? = next

}


object ProviderChainOfCheckers {

    fun contentCheckers(): Checker = CheckEmailAttachment(CheckEmailAttachment(null, content = "data"))
}