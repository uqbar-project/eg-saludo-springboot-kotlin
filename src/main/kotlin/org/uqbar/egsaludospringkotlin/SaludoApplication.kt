package org.uqbar.egsaludospringkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SaludoApplication

fun main(args: Array<String>) {
	runApplication<SaludoApplication>(*args)
}
