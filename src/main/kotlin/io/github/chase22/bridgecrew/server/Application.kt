package io.github.chase22.bridgecrew.server

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("io.github.chase22.bridgecrew.server")
                .mainClass(Application.javaClass)
                .start()
    }
}