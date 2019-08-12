package io.github.chase22.bridgecrew.server.subsystem

import io.github.chase22.bridgecrew.server.ship.Ship
import javax.inject.Singleton

abstract class EnergyProductionSubsystem(id: String): Subsystem(id) {
    abstract fun getEnergyProduced(): Long

    override fun getTypes(): List<SubsystemType> {
        return listOf(SubsystemType.ENERGY_PRODUCTION)
    }
}

@Singleton
class SolarPanel: EnergyProductionSubsystem("solar") {
    override fun getEnergyProduced(): Long = 100
    override fun getTemperatureChange(): Int = 0
    override fun getEnergyConsumption(): Long = 0
    override fun getTypes(): List<SubsystemType> = listOf(SubsystemType.ENERGY_PRODUCTION)

    override fun updateInternal(context: Ship) {}
}