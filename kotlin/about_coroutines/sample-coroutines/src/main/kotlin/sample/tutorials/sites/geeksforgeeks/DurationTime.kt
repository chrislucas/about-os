package sample.tutorials.sites.geeksforgeeks

import kotlin.time.Duration

/*
    https://www.geeksforgeeks.org/duration-parsecharsequence-method-in-java-with-examples/#
    https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html

https://www.logicbig.com/how-to/code-snippets/jcode-java-8-date-time-api-duration-parse.html
    Obtains a Duration from a text string of pattern: PnDTnHnMn.nS, where
    nD = number of days,
    nH = number of hours,
    nM = number of minutes,
    n.nS = number of seconds, the decimal point may be either a dot or a comma.
    T = must be used before the part consisting of nH, nM, n.nS
 */

fun getDuration(pattern: String) = Duration.parse(pattern)


fun main() {
    arrayOf(
        "P4DT12H20M20.3S", "PT20S", "P2DT20S", "P2DT24H20S", "PT240H", "PT3600S",
        "PT86400S"
    ).forEach {
        println(getDuration(it))
    }
}