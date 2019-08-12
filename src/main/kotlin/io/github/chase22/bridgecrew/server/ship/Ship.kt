package io.github.chase22.bridgecrew.server.ship

import io.github.chase22.bridgecrew.server.base.Updateable
import io.github.chase22.bridgecrew.server.subsystem.EnergyProductionSubsystem
import io.github.chase22.bridgecrew.server.subsystem.Subsystem
import io.github.chase22.bridgecrew.server.subsystem.SubsystemRvo
import io.github.chase22.bridgecrew.server.subsystem.SubsystemType
import io.github.chase22.bridgecrew.server.subsystem.SubsystemType.ENERGY_PRODUCTION

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
        val energyProduction: List<Subsystem>? = getSubsystemsByType(ENERGY_PRODUCTION)
        energyProduction?.forEach { it.update(this) }

        energy = energyProduction?.filterIsInstance(EnergyProductionSubsystem::class.java)
                ?.map { it.getEnergyProduced() }?.sum() ?: 0

        val energyConsumed = subsystem.map(Subsystem::getEnergyConsumption).sum()

        if (energyConsumed > energy) {

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
