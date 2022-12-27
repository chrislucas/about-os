package sample.rx.studies.flowable

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers

fun main() {
    Flowable.range(1, 10)
        .observeOn(Schedulers.computation())
        .map { v -> v * v }
        .blockingSubscribe(System.out::println)
}