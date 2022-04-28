package sample.book.kcoroutines.tutorials.suspendfunworks.resumingwithvaluew.feature.mockingrequestuser

import kotlinx.coroutines.delay
import java.util.concurrent.Executors

suspend fun <T> doRequest(request: suspend () -> T) = request()

suspend fun mockHttpRequestUser(): User {
    delay(3000L)
    return User("chrislucas")
}

private val executor = Executors.newSingleThreadScheduledExecutor {
    Thread(it, "scheduler").apply { isDaemon = true }
}