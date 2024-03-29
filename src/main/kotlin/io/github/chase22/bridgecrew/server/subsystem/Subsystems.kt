package io.github.chase22.bridgecrew.server.subsystem

import io.github.chase22.bridgecrew.server.ship.Ship
import io.github.chase22.bridgecrew.server.subsystem.SubsystemType.TEMPERATURE_MANAGEMENT
import javax.inject.Singleton

@Singleton
class Radiator: Subsystem("radiator") {
    override fun getTemperatureChange(): Int = 0
    override fun getEnergyConsumption(): Long = 0
    override fun getTypes(): List<SubsystemType> = listOf(TEMPERATURE_MANAGEMENT)

    override fun updateInternal(context: Ship) {}
}