package sample.ktutorial.docs.flow;


/*
    flow vs sequence

    https://kotlinlang.org/docs/flow.html#flows

    Uma funcao que retornar um List<Int> o retorna de uma unica vez

    "To represent the stream of values that are being asynchronously computed, we can use a  Flow<Int>"

    A diferença entre a funcao builder flow {} e sequence {} e simplesmente que a primeira funciona de forma
    assincrona e a segunda de forma sincrona


    https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html

    Flow constraints

    - "Todas as implementacoes da interface Flow devem aderir a duas propriedades chaves"
        -> Context Presernvation
        -> Exception transparency
    - Essas propriedades garantem a capacidade de construicao da logia do codigo flow
    de tal maneira que possamos modulariza-lo
        - a parte do código que emite o upstream flow pode ser desenvolvida separadamente
        da coleta de downstream flow

        - Um usuario que recupera o flow nao precisa estar ciente de cetalhes de implementacao
        do upstream flow

    Flow builders
    https://kotlinlang.org/docs/flow.html#flow-builders


    Context Preservation
    https://kotlinlang.org/docs/flow.html#flow-context

 */