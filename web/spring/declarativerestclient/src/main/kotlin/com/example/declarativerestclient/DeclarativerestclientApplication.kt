package com.example.declarativerestclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DeclarativerestclientApplication


/**
 * Projeto baseado no link abaixo
 * 	https://medium.com/digitalfrontiers/declarative-rest-clients-with-spring-framework-6-c671be1dfee
 *
 * 	Rest Client Spring Docs
 * 	https://docs.spring.io/spring-framework/docs/6.0.0/reference/html/integration.html#rest-http-interface
 */
fun main(args: Array<String>) {
	runApplication<DeclarativerestclientApplication>(*args)
}
