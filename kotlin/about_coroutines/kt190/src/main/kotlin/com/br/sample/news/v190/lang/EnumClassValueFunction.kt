package com.br.sample.news.v190.lang


/*
    https://kotlinlang.org/docs/whatsnew19.html#stable-replacement-of-the-enum-class-values-function

    A proriedade 'entries' foi adiciionada a classes Enum na versao 1.8.20 como uma feature experimental

    Essa propriedade e uma substituicao mais moderna e com melhor desempenho do que a funcao 'values()'

 */


enum class ColorTip(val color: String) {
    RED("#ff0000"),
    GREEN("#ff0000"),
    YELLOW("#ff0000"),
    BLUE("#ff0000"),
}

enum class Tip(val color: ColorTip, val message: String) {
    WARNING(ColorTip.YELLOW, "Warning"),
    CAUTION(ColorTip.BLUE, "Caution"),
    ERROR(ColorTip.RED, "Error"),
    SUCCESS(ColorTip.GREEN, "Success"),
}

private fun findTipByColor(color: ColorTip) = Tip.entries.find { it.color == color }

fun main() {

    println(findTipByColor(ColorTip.YELLOW))
    println(findTipByColor(ColorTip.BLUE))
}