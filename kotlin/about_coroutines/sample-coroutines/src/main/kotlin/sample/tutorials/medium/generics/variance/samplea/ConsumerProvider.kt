package sample.tutorials.medium.generics.variance.samplea


/*
    https://proandroiddev.com/understanding-type-variance-in-kotlin-d12ad566241b
    Uma classe pode ser covariante em um arqumento e contravariante em outro
    C - consumer - contravariante
    P - Producer - Covariante
 */
interface ConsumerProvider<in C, out P> {
    fun invoke(value: C): P
}