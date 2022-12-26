package sample.ktutorial.ktacademy.tutorial.mutability;

/*
   https://kt.academy/article/ek-mutability

   Algumas razoes para preferir imutabilidade ao inves de mutabilidade
   - Objetos imutaveis sao perfeitos para contrucao de outros objetos, mutaveis ou imutaveis.
   Podemis decidir para esses novos objetos os seus pon tos de mutabilidade

   - Podemos usar objetos imutaveis como chaves de estruturas de chave e valor.
       - Essas estruturas usam algoritmos de hash usando o conteudo definido como a chave
       - Usar objetos que não mudam de estado garante a confiabilidade dessas chaves
       - As chaves precisam ser imutaveis para que os valores possam ser recuperados
*/
