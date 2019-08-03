package io.github.chase22.bridgecrew.server.base

interface Updateable<T> {
    fun update(context: T)
}