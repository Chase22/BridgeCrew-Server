package io.github.chase22.bridgecrew.server.ship

import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class InMemoryShipRepository() : ShipRepository {
    private val ships: MutableMap<String, ShipEntity> = HashMap()

    init {
        ships["someId"] = ShipEntity(
                "someId", 100, 150, 300, 100, 100, emptyList()
        )
    }

    override fun findById(id: String): Maybe<ShipEntity> {
        return ships[id]?.let { Maybe.just(it) }?: Maybe.empty()
    }

    override fun getAll(id: String): Single<List<ShipEntity>> {
        return Single.just(ships.values.toList())
    }

    override fun save(entity: ShipEntity): Single<ShipEntity> {
        ships[entity.id] = entity
        return Single.just(entity)
    }
}