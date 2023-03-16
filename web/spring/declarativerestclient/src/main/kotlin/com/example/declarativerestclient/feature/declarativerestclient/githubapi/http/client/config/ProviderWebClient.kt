package com.example.declarativerestclient.feature.declarativerestclient.githubapi.http.client.config

import org.springframework.http.HttpStatusCode
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.function.Function
import java.util.function.Predicate

private fun defaultPredicateHttpStatusCode() =
    Predicate<HttpStatusCode> { status ->
        status.isError
    }

private fun errorHandlerForEmptyState() =
    Function<ClientResponse, Mono<out Throwable>> { _ ->
        Mono.empty()
    }


private fun errorHandlerForInternalError() =
    Function<ClientResponse, Mono<out Throwable>> { response ->
        Mono.error(Exception("Error: ${response.statusCode()}"))
    }

fun defaultWebClient(): WebClient {
    /*
        https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/function/client/support/WebClientAdapter.html
     */
    return WebClient.builder()
        .defaultStatusHandler(
            defaultPredicateHttpStatusCode(), errorHandlerForEmptyState()
        )
        .defaultStatusHandler(
            HttpStatusCode::is5xxServerError, errorHandlerForInternalError()
        )
        .build()
}

fun defaultWebClient(baseUrl: String): WebClient {
    return WebClient.builder()
        .baseUrl(baseUrl)
        .defaultStatusHandler(
            defaultPredicateHttpStatusCode(), errorHandlerForEmptyState()
        )
        .defaultStatusHandler(
            HttpStatusCode::is5xxServerError, errorHandlerForInternalError()
        )
        .build()
}