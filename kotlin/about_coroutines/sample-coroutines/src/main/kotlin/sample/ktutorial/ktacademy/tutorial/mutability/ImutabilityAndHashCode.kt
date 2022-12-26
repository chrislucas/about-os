package sample.ktutorial.ktacademy.tutorial.mutability

import java.util.SortedSet
import java.util.TreeSet

data class Fullname(var fistName: String, val lasName: String) : Comparable<Fullname> {
    override fun compareTo(other: Fullname): Int =
        "$fistName $lasName".compareTo("${other.fistName} ${other.lasName}")

}

/*
    https://kt.academy/article/ek-mutability
 */

private fun breakContractWithHashCode() {
    val names: SortedSet<Fullname> = TreeSet()
    val person = Fullname("AAA", "luccas")

    names += person
    names += Fullname("Test", "abc")
    names += Fullname("james", "webb")
    //names += Fullname("alan", "turing")
    //names += Fullname("Marie", "Currie")
    println(names)
    println(person in names)

    person.fistName = "ZZZ"
    println(names)
    println(person in names)

}

private fun check() {
    val names: SortedSet<Fullname> = TreeSet()
    val person = Fullname("AAA", "luccas")
    names += person
    names += Fullname("Jordan", "Hansen")
    names += Fullname("David", "Blanc")
    names += Fullname("james", "webb")
    names += Fullname("alan", "turing")
    names += Fullname("Marie", "Currie")

    println(names) // [AAA AAA, David Blanc, Jordan Hansen]
    println(person in names) // true

    person.fistName = "ZZZ"
    println(names) // [ZZZ AAA, David Blanc, Jordan Hansen]
    println(person in names) // false
}


fun main() {
    breakContractWithHashCode()
    println("****************************************")
    check()
}