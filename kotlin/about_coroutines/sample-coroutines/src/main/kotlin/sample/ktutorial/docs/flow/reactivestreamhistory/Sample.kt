package sample.ktutorial.docs.flow.reactivestreamhistory

/*

    Reactive Streams
    https://www.reactive-streams.org/

    https://kotlinlang.org/docs/flow.html#flow-and-reactive-streams

    O design da api flow tem inspiracao na Reactive Stream e suas implementacoes.
    Com o principal objetivo de ser tao simples quanto possivel, sendo compativel
    com suspend function e respeitando a estruura de concorrencia.

    Conceitualmente diferente, flow is reactive stream e possivel de ser convertido
    para reactive

    https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html
    "Flow is Reactive Streams compliant, you can safely interop it with reactive
    streams using Flow.asPublisher and Publisher.asFlow from kotlinx-coroutines-reactive module."

    Reactive Streams and Kotlin Flows
    https://elizarov.medium.com/reactive-streams-and-kotlin-flows-bfd12772cda4

    -------------------------------------------------------------------------------------------
    upstream flow and return a downstream flow.
    https://developer.android.com/kotlin/flow?hl=de
    https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/
 */