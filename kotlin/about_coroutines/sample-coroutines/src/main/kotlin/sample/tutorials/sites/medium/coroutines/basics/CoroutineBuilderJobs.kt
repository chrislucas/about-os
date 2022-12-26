package sample.tutorials.sites.medium.coroutines.basics

import kotlinx.coroutines.*
import sample.ktutorial.about
import sample.ktutorial.childrenToString
import sample.ktutorial.logCoroutineScope
import kotlin.coroutines.CoroutineContext

/*
    https://kt.academy/article/cc-job
    Coroutine builders create their jobs based on their parent job

 */


private fun checkAsyncCoroutineBuilder() {
    runBlocking {
        logCoroutineScope("Runblocking block")
        val asyncGetText = async {
            delay(3000)
            "Hellow, ${0b11000000001}"
        }


        with(asyncGetText) {
            val job: Job = this
            println(job.about())
            println(await())
            println(job.about())
        }
    }
}

/*
    Job é o unico CoroutineContext que nao herda de uma coroutina. Cada coroutine
    cria o seu proprio Job
 */

private fun checkJobInheritance() {

    runBlocking {
        val context : CoroutineContext = CoroutineName("My Coroutine")
        val job = Job()
        launch (context + job) {
            val childName = coroutineContext[CoroutineName]
            logCoroutineScope("ChildName == CoroutineContext ? ${childName == context}")
            val childJob = coroutineContext[Job]
            logCoroutineScope("ChildJob == Job ? ${childJob == job}")
            logCoroutineScope("ChildJob == First Job Children ? ${childJob == job.children.first()}")
            println("******************************************************************************************\n")
            logCoroutineScope("Children: ${job.children.joinToString(",")}")
        }
    }
}


private fun checkCoroutineInheritance() {
    runBlocking {
        val jobA = launch (start = CoroutineStart.LAZY) {
            delay(1000)
            println("Job A Scope: $this")
        }

        launch {
            logCoroutineScope("it's the Job B $this")
            jobA.start()
            logCoroutineScope("Job A: ${jobA.about()}")
        }
    }
}


private fun parentJob() {
    fun sampleRunBlockingLaunchingCoroutine() {
        runBlocking {
            val job1 = launch(CoroutineName("Job 1")) {
                delay(1000)
                logCoroutineScope("job 1: ${this.coroutineContext.job.childrenToString()}")
            }
            job1.start()

            val job2 = launch(CoroutineName("Job 2")) {
                launch {
                    logCoroutineScope("Launch coroutine inside Job 2")
                }.join()
                delay(1000)
                logCoroutineScope("job 2: ${this.coroutineContext.job.childrenToString()}")
            }
            job2.start()

            val job3ReplacingCoroutineContext = launch(context = Dispatchers.IO) {
                launch {
                    logCoroutineScope("Launch coroutine inside Job 3")
                }
                delay(1000)
                logCoroutineScope("job 3: ${this.coroutineContext.job.childrenToString()}")

            }
            job3ReplacingCoroutineContext.start()

            /* val job4ReplacingCoroutineContext = */launch(context = Job()) {
                delay(1000)
                logCoroutineScope("job 4: ${this.coroutineContext.job.childrenToString()}")
            }
            // nao vou iniciar o Job 4
            //job4ReplacingCoroutineContext.start()

            // nao vou iniciar esse job 5
           launch(context = Job()) {
                delay(1000)
                logCoroutineScope("job 5: ${this.coroutineContext.job.childrenToString()}")
            }

            /*
                O interessante eh q o Job 4 e 5 nao aparecem na lista, porque o contexto foi
                mudado para uma nova instancia de Job, mas o Job 3
             */
            logCoroutineScope("Child Jobs: ${coroutineContext.job.childrenToString()}")
        }
    }

    sampleRunBlockingLaunchingCoroutine()
}

private fun checkAnotherExampleParentRelationship() {

     suspend fun lauchingCoroutines() {
        CoroutineScope(Dispatchers.IO).launch(CoroutineName("Coroutine Parent")) {

            launch(CoroutineName("Job 1")) {
                delay(1000)
                logCoroutineScope("job 1: ${this.coroutineContext.job.childrenToString()}")
            }


            launch(CoroutineName("Job 2")) {
                delay(1000)
                logCoroutineScope("job 2: ${this.coroutineContext.job.childrenToString()}")
            }

            logCoroutineScope("Child Jobs of Coroutine Parent: ${coroutineContext.job.childrenToString()}")
        }.join()
    }

    runBlocking {
        lauchingCoroutines()
        logCoroutineScope("Runblocking Child Jobs: ${coroutineContext.job.childrenToString()}")
    }
}

fun main() {
    //checkAsyncCoroutineBuilder()
    //checkCoroutineInheritance()
    //checkJobInheritance()
    parentJob()
    //checkAnotherExampleParentRelationship()
}