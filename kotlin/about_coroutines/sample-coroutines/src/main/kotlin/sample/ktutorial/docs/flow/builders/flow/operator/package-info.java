package sample.ktutorial.docs.flow.builders.flow.operator;

/*
    https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html

    interface Flow<out T>

    - Um data stream assincrono que emite sequencialmente valores
        - a operacao de emição de valores pode terminar de 2 formas
            - normalmente como qualquer outra funcao
            - caso haja um comportamento inesperado lanca-se uma exception

    Na api padrao existem funcoes de extensao chamadas de intermediate operators
        - map, filter, zip, reduce take
    Sao funcoes que sao aplicadas em UPSTREAM FLOW () ou FLOWS e retornam um DOWNSTREAM FLOW ()
    onde mais operacoes podem ser aplicadas

    Intermediate operators nao executam nenhum codigo num FLOW e nao sao suspend functions
        - Elas definem uma cadeia de operacoes para eecucoes futuras e rapidamente retornam para o fluxo de execucao
        - Isso eh conhecido como COLD FLOW PROPERTY

    # Terminal Operators
        - collect, single, reduce, toList
 */