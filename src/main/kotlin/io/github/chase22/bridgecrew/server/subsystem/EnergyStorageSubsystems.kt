package io.github.chase22.bridgecrew.server.subsystem

import io.github.chase22.bridgecrew.server.ship.Ship
import javax.inject.Singleton

abstract class EnergyStorageSubsystem(id: String): Subsystem(id) {
    override fun getTypes(): List<SubsystemType> = listOf(SubsystemType.ENERGY_STORAGE)

    abstract fun getStorageCapacity(): Long
}

@Singleton
class Battery: EnergyStorageSubsystem("battery") {
    override fun getStorageCapacity() = 100L
    override fun getTemperatureChange(): Int = 0
    override fun getEnergyConsumption(): Long = 0

    override fun updateInternal(context: Ship) {}
}