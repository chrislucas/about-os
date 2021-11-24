package sample.ktutorial.docs.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import sample.ktutorial.logCoroutineScope

/*

 */


private fun simpleCountWithFlow() = flow {
    logCoroutineScope("simpleCountWithFlow")
    for (i in 0..10) {
        delay(400L)
        emit("Flow $i")
    }
}

private fun CoroutineScope.fakeCompute() {
    launch {
        logCoroutineScope("CoroutineScope Ext - fakeCompute")

        for (i in 0..10) {
            println("Checking Thread with flow $i")
            delay(1000L)
        }
    }
}


private suspend fun suspendFunFakeCompute() {
    logCoroutineScope("SuspendFunFakeCompute")
    for (i in 0..10) {
        println("Checking Thread with flow $i")
        delay(1000L)
    }
}


private fun checkBehaviorExtFakeCompute() = runBlocking {
    fakeCompute()
    simpleCountWithFlow().collect {
        println(it)
    }
}

private fun checkBehaviorExtFakeComputeWithLaunch() = runBlocking {
    launch {
        fakeCompute()
    }

    simpleCountWithFlow().collect {
        println(it)
    }
}


private fun checkBehaviorSuspendFunFakeCompute() = runBlocking {
    suspendFunFakeCompute()

    simpleCountWithFlow().collect {
        println(it)
    }
}

private fun checkBehaviorSuspendFunFakeComputeWithLaunch() = runBlocking {

    launch {
        suspendFunFakeCompute()
    }

    simpleCountWithFlow().collect {
        println(it)
    }
}


fun main() {
    //checkBehaviorSuspendFunFakeCompute()
    checkBehaviorSuspendFunFakeComputeWithLaunch()
}

