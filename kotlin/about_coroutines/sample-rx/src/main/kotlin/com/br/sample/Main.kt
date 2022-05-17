package com.br.sample

import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.kotlin.toObservable

fun main() {
    // https://github.com/ReactiveX/RxKotlin
    val disposable = listOf(
        "Christopher", "Daniel",
        "Jefferson", "Antonio", "Marielle", "Antonietta"
    )
        .toObservable()
        .filter { it.length > 7 }
        .subscribeBy(
            onNext = { println(it) },
            onError = { it.printStackTrace() },
            onComplete = { println("Done") }
        )

    disposable.dispose()
}