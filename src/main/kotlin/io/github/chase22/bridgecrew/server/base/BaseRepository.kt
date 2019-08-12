package io.github.chase22.bridgecrew.server.base

import io.reactivex.Maybe
import io.reactivex.Single

interface BaseRepository<Key, Entity> {
    fun findById(id: Key): Maybe<Entity>
    fun getAll(id: Key): Single<List<Entity>>
    fun save(entity: Entity): Single<Entity>
}