package io.github.chase22.bridgecrew.server.subsystem

import io.github.chase22.bridgecrew.server.base.Updateable
import io.github.chase22.bridgecrew.server.ship.Ship

abstract class Subsystem(
        val id: String,
        var active: Boolean
) : Updateable<Ship> {
    abstract fun getTemperatureChange(): Int
    abstract fun getEnergyConsumption(): Long
    abstract fun getTypes(): List<SubsystemType>
    abstract fun updateInternal(context: Ship)

    override fun update(context: Ship) {
        if (context.energy < getEnergyConsumption()) {
            active = false
        }
        updateInternal(context)
        context.energy -= getEnergyConsumption()
    }
}

enum class SubsystemType {
    ENERGY_PRODUCTION,
    OTHER,
    TEMEPERATURE_MANAGEMENT
}