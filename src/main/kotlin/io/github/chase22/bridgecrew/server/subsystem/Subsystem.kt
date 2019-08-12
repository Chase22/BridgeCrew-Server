package io.github.chase22.bridgecrew.server.subsystem

import io.github.chase22.bridgecrew.server.base.Updateable
import io.github.chase22.bridgecrew.server.ship.Ship

abstract class Subsystem(
        val id: String
) : Updateable<Ship> {
    abstract fun getTemperatureChange(): Int
    abstract fun getEnergyConsumption(): Long
    abstract fun getTypes(): List<SubsystemType>
    protected abstract fun updateInternal(context: Ship)

    override fun update(context: Ship) {
        updateInternal(context)
    }

    fun toRvo(): SubsystemRvo {
        return SubsystemRvo(id, getTypes().map { it.name })
    }
}

data class SubsystemRvo(
        val id: String,
        val types: List<String>
)

enum class SubsystemType {
    ENERGY_PRODUCTION,
    OTHER,
    TEMPERATURE_MANAGEMENT,
    ENERGY_STORAGE
}