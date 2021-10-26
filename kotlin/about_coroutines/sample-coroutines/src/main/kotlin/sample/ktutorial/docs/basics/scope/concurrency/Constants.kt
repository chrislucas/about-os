package sample.ktutorial.docs.basics.scope.concurrency


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/uuuu HH:mm:ss")

val localDateTimeNow: String
    get() = dtf.format(LocalDateTime.now())



val coroutineScopeIO = CoroutineScope(Dispatchers.IO)