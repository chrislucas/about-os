package com.example.declarativerestclient.feature.declarativerestclient.githubapi.http.client.config

import com.example.declarativerestclient.feature.declarativerestclient.githubapi.http.client.ReleaseRepositoryClient
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory


@Configuration
class BindWebClientToDeclarativeHttpClient {

    /*
        https://medium.com/digitalfrontiers/declarative-rest-clients-with-spring-framework-6-c671be1dfee
        Criando a ligacao do WebClient com o Http Clent Declarativo ReleaseRepositoryClient

        O webclient eh gerado usadndo o builder da classe HttpServiceProxyFactory
     */
    companion object {
        fun bind(): ReleaseRepositoryClient {
            return HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(defaultWebClient()))
                .build()
                .createClient(ReleaseRepositoryClient::class.java)
        }
    }

}