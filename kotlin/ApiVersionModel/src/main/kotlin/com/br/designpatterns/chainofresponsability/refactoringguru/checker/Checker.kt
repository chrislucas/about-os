package com.br.designpatterns.chainofresponsability.refactoringguru.checker

interface Checker {
    fun validate()

    fun solver()
    fun next(): Checker?
}
