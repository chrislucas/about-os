package sample.tutorials.medium.generics.variance


/*
    Criando uma interface parametrizavel covariante onde aceita T e seus subtipos
    o que implica que
     - se U for subtupo de T entao Producer<U> também é um subtitpo de Producer<T>
 */
interface ReadOnly<out T> {
    fun producer(): T
}