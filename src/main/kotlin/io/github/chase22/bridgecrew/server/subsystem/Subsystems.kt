package io.github.chase22.bridgecrew.server.subsystem

import io.github.chase22.bridgecrew.server.ship.Ship
import io.github.chase22.bridgecrew.server.subsystem.SubsystemType.*
import javax.inject.Singleton

@Singleton
class SolarPanel: Subsystem("solar") {
    override fun getTemperatureChange(): Int = 0
    override fun getEnergyConsumption(): Long = 0
    override fun getTypes(): List<SubsystemType> = listOf(ENERGY_PRODUCTION)

    override fun updateInternal(context: Ship) {}
}

@Singleton
class Battery: Subsystem("battery") {
    override fun getTemperatureChange(): Int = 0
    override fun getEnergyConsumption(): Long = 0
    override fun getTypes(): List<SubsystemType> = listOf(ENERGY_STORAGE)

    override fun updateInternal(context: Ship) {}
}

@Singleton
class Radiator: Subsystem("radiator") {
    override fun getTemperatureChange(): Int = 0
    override fun getEnergyConsumption(): Long = 0
    override fun getTypes(): List<SubsystemType> = listOf(TEMPERATURE_MANAGEMENT)

    override fun updateInternal(context: Ship) {}
}