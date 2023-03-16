package com.br.designpatterns.chainofresponsability.refactoringguru.checker

abstract  class TemplateChecker: Checker {

    abstract fun canISolver(): Boolean

    override fun validate() {
        if (canISolver()) {
            solver()
        } else {
            next()
        }
    }
}