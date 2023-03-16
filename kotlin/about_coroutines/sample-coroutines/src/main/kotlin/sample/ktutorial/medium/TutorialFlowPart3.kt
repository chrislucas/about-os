package sample.ktutorial.medium
/*
    https://medium.com/@ahmadkazimi/all-you-need-to-know-about-kotlin-flow-part-3-8c549648317a

    State Flow - State Holder observable Flow
    definicao nao formal: "Um observador de estado de fluxo que EMITE o estado atual e atualizacoes
    de estado para um COLETOR"

    O autor compara o StateFlow com o LiveData do Android porém sem "o mecanismo de conhecimento de ciclo
    de vida".

    A estrutura do StateFlow mantem um unico valor de estao. e isso torna-o um HotFlow

        - ColdFlow: Um 'Cold Stream' nao comeca a produzir valor ate que alguem comece a coletar
        dados dele

        - HotFlow: Um HotStream inicia a produzir valor imediatamente
 */


fun main() {

}