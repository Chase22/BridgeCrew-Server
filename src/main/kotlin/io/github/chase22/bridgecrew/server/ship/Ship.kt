package io.github.chase22.bridgecrew.server.ship

import io.github.chase22.bridgecrew.server.base.Updateable
import io.github.chase22.bridgecrew.server.subsystem.Subsystem
import io.github.chase22.bridgecrew.server.subsystem.SubsystemType

data class Ship(
        var energy: Long,
        var temperature: Int,
        val maxTemperature: Int,
        var hullPoints: Long,
        val maxHullPoints: Long,
        var alive: Boolean,
        var subsystem: List<Subsystem>
) : Updateable<Unit> {

    constructor(shipRvo: ShipRvo): this(
            shipRvo.energy,
            shipRvo.temperature,
            shipRvo.maxTemperature,
            shipRvo.hullPoints,
            shipRvo.maxHullPoints,
            shipRvo.alive,
            shipRvo.subsystem ?: ArrayList()
    )

    override fun update(context: Unit) {
        subsystem.sortedBy { subsystem ->
            subsystem.getTypes().minBy { subsystemType -> subsystemType.ordinal }!!
        }.forEach {
            temperature += it.getTemperatureChange()
            it.update(this)
        }
    }

    fun getSubsystemsByType(type: SubsystemType): List<Subsystem>? =
            subsystem.filter { subsystem -> subsystem.getTypes().contains(type) }

    fun toRvo(): ShipRvo {
        return ShipRvo(energy, temperature, maxTemperature, hullPoints, maxHullPoints, alive, subsystem)
    }
}