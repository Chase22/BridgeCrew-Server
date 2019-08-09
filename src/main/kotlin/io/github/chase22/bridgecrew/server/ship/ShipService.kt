package io.github.chase22.bridgecrew.server.ship

import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShipService @Inject constructor(private val shipRepository: ShipRepository) {

    fun getShip(id: String): Maybe<Ship> = shipRepository.findById(id).map { entity -> Ship(entity) }

    fun save(ship: Ship): Single<Ship> = shipRepository.save(ship.toEntity()).map { entity -> Ship(entity) }
}