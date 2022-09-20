package sample.tutorials.sites.medium.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import sample.ktutorial.logCoroutineScope
import sample.ktutorial.logThreadScope


/**
 * Em qual thread a funcao main esta sendo executada ?
 * Em qual thread a funcao suspensa fakeTask esta sendo executada ?
 *
 * Ambas estao na mesma thread
 *
 */
suspend fun fakeTask() {
    logCoroutineScope("Before start Fake Task")
    delay(2000)
    logCoroutineScope("After end Fake Task")
}

private fun checkFakeTask() {
    runBlocking {
        logThreadScope("Before execute fakeTask")
        fakeTask()
        logThreadScope("After execute fakeTask")
    }
}


fun main() {
    logThreadScope("Main Function")
    checkFakeTask()
}