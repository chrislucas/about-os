package com.br.sample.news.v190.lang

/*
    Stable data objects for symmetry with data classes
    https://kotlinlang.org/docs/whatsnew19.html?lidx=1&wpid=370647&mkt_tok=NDI2LVFWRC0xMTQAAAGM7hLypcl-sEqJSoE1DI7BTkHDS4qvcQ1E6Mo12c4wo6ZtLUkVRZRsAHKe-SjHReF3La4ANDMGJnZE_muX7rgRxtk2asgEJFh_olUpSN6FLucInBHP#stable-data-objects-for-symmetry-with-data-classes

    Data object foi incluida na versao 1.8.20 do kotlin e agora esta estavel, isso inclui
    a adicao dos metodos toString(), equals() e hashCode() para criar uma simetria com data
    classes.


 */


sealed interface Reader

private data class IntegerReader(val value: Int) : Reader
private data class TextReader(val value: String) : Reader

/*
    Declarar EndOfFile como data object ao inves de plain object da ao objeto
    o metodo toString() sem a necessidade de sobreescreve-lo manualmente.
    Isso mantém uma simetria entre
 */
private data object EndOfFIle : Reader

private object ByteReader: Reader {
    // sem o toString o compilador sugere que transforme-mos esse object
    override fun toString() = "Hello, world"
}

private fun checkDataType() {
    println(IntegerReader(1))
    println(TextReader("Ola, mundo"))
    println(EndOfFIle)
    println(ByteReader)
}


fun main() {
    checkDataType()
}