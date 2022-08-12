package sample.tutorials.sites.medium.flow;

/*


   ------------------------------------------------------------------------------------------
   Flow
   https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/


   Um data stream assincrono que sequencialmente emite valores e finaliza normalmente ou com uma exception.

   - Intermediate operatos
       - map, filter, take, zip
       - Essas sao funcoes/operadores que sao aplicadas ao UPSTREAM FLOW(S) e retornam um DOWNSTREAM FLOW
           - esses operadores podem continuar sendo aplicados ao DOWNSTREAM FLOW resultante
       - Intermediate operators nao executam codigo no flow e nao sao suspending functions
           - Apenas definem uma cadeia de operacoes para serem executadas no futuro e retornam rapidamente
           - Essa propriedade é conhecida como COLD FLOW PROPERTY

   - Terminal Operators
       - collect, single, reduce,toList ou launchIn
       - Sao suspend function que iniciam a coleta no flow em um dado escopo (IO, Main)
       - Esses operadores sao aplicados ao UPSTREAM FLOW e disparam a execucao de todas as operacoes

       - A execucao do flow é tambem cahcama de coleta do mesmo e é sempre feita de uma forma "suspensa", operando
       de forma nao bloqueante

       - Terminal operator terminam sua execucao normalmente dependendo do sucesso
       da execucao de todas as operacoes no UPSTREAM FLOW ou terminam lancando uma excecao.

       - Por padrao o flow e sequencial assim como todas as operacoes dele sao executadas sequencialmente na
       mesma coroutine
           - Existe uma excecao para algumas poucas operacoes especificamente projetadas para para introduzir
           concorrencia a execucao do flow


   ------------------------------------------------------------------------------------------
   https://medium.com/swlh/introduction-to-flow-channel-and-shared-stateflow-e1c28c5bc755#:~:text=The%20main%20difference%20between%20flows,produced%20outside%20of%20the%20stream.
  ------------------------------------------------------------------------------------------

  ------------------------------------------------------------------------------------------
   Flow e Channel definicoes e diferencas

   ------------------------------------------------------------------------------------------

   ------------------------------------------------------------------------------------------
   ChannelFlow
   https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/channel-flow.html

   ------------------------------------------------------------------------------------------

   ------------------------------------------------------------------------------------------
   Channel
   https://kotlinlang.org/docs/channels.html#closing-and-iteration-over-channels
   ------------------------------------------------------------------------------------------

   Cold stream and Hot Stream
   https://stackoverflow.com/questions/69297362/what-is-the-hot-flow-and-cold-flow-in-coroutines-and-the-difference-between-them


   ------------------------------------------------------------------------------------------


*/
