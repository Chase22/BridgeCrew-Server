package io.github.chase22.bridgecrew.server.ship

import io.github.chase22.bridgecrew.server.subsystem.SubsystemRegistry
import io.reactivex.Maybe
import io.reactivex.Single
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShipService @Inject constructor(
        private val shipRepository: ShipRepository,
        private val subsystemRegistry: SubsystemRegistry) {

    fun getShip(id: String): Maybe<Ship> = shipRepository.findById(id).map(this::fromEntity)

    fun save(ship: Ship): Single<Ship> = shipRepository.save(ship.toEntity()).map(this::fromEntity)

    fun fromRvo(shipRvo: ShipRvo): Ship {
        val ship = Ship(
                shipRvo.id,
                shipRvo.energy,
                shipRvo.temperature,
                shipRvo.maxTemperature,
                shipRvo.hullPoints,
                shipRvo.maxHullPoints,
                shipRvo.subsystem?.mapNotNull { subsystemRegistry.getById(it.id) }?:ArrayList()
        )

        shipRvo.subsystem?.filterNot { ship.subsystem.map { subsystem -> subsystem.id }.contains(it.id) }
                ?.forEach { LOGGER.warn("No matching subsystem for id ${it.id}") }

        return ship

    }

    private fun fromEntity(shipEntity: ShipEntity): Ship {
        val ship = Ship(
                shipEntity.id,
                shipEntity.energy,
                shipEntity.temperature,
                shipEntity.maxTemperature,
                shipEntity.hullPoints,
                shipEntity.maxHullPoints,
                shipEntity.subsystem.mapNotNull { subsystemRegistry.getById(it) }
        )

        shipEntity.subsystem
                .filterNot { ship.subsystem.map { subsystem -> subsystem.id }.contains(it) }
                .forEach { LOGGER.warn("No matching subsystem for id $it") }

        return ship
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(ShipService::class.java)
    }
}