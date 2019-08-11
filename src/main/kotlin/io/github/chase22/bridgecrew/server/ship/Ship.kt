package io.github.chase22.bridgecrew.server.ship

import io.github.chase22.bridgecrew.server.base.Updateable
import io.github.chase22.bridgecrew.server.subsystem.Subsystem
import io.github.chase22.bridgecrew.server.subsystem.SubsystemRvo
import io.github.chase22.bridgecrew.server.subsystem.SubsystemType

data class Ship(
        val id: String,
        var energy: Long,
        var temperature: Int,
        val maxTemperature: Int,
        var hullPoints: Long,
        val maxHullPoints: Long,
        var subsystem: List<Subsystem>
) : Updateable<Unit> {

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
        return ShipRvo(id, energy, temperature, maxTemperature, hullPoints, maxHullPoints, subsystem.map { it.toRvo() })
    }

    fun toEntity(): ShipEntity {
        return ShipEntity(id, energy, temperature, maxTemperature, hullPoints, maxHullPoints, subsystem.map { it.id })
    }
}

data class ShipRvo (
        val id: String,
        val energy: Long,
        val temperature: Int,
        val maxTemperature: Int,
        val hullPoints: Long,
        val maxHullPoints: Long,
        val subsystem: List<SubsystemRvo>?
)

data class ShipEntity (
        val id: String,
        val energy: Long,
        val temperature: Int,
        val maxTemperature: Int,
        val hullPoints: Long,
        val maxHullPoints: Long,
        val subsystem: List<String>
)
