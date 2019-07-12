package com.bcloutier.swjam

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SwjamApplication

fun main(args: Array<String>) {
	runApplication<SwjamApplication>(*args)
}
