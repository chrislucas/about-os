package sample.tutorials.medium.generics.variance.samplea


/*
    https://proandroiddev.com/understanding-type-variance-in-kotlin-d12ad566241b
    Uma classe pode ser covariante em um arqgumento e contravariante em outro
 */
interface ConsumerProvider<in ContraVariance, out Covariance> {
    fun invoke(value: ContraVariance): Covariance
}