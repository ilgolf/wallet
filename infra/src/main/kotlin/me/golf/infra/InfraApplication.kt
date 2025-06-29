package me.golf.infra

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class InfraApplication

fun main(args: Array<String>) {
    runApplication<InfraApplication>(*args)
}
