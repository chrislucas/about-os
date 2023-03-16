package com.br.feature.versioningfeature.app

import com.br.feature.versioningfeature.models.Api
import com.br.feature.versioningfeature.models.Metada


/**
  ttps://refactoring.guru/pt-br/design-patterns/chain-of-responsibility

    Ideia principal: Modelar uma solução para gereciar versionamento de API utilizando de técnicas de abstracoes
 para projetar uma solução orientada a objetos que seja fácul de entender e acrescententar nova implementacao de API

 */

fun main() {
    val api: List<Api> = listOf(
        Api.Cart(Metada("", "0.1.0", false)),
        Api.Cart(Metada("", "0.2.0", true)),
    )
}