package io.github.chase22.bridgecrew.server.ship

import io.github.chase22.bridgecrew.server.base.Updateable
import io.github.chase22.bridgecrew.server.subsystem.Subsystem

data class Ship(
        var energy: Long,
        var temperature: Int,
        val maxTemperature: Int,
        var hullPoints: Long,
        val maxHullPoints: Long,
        var alive: Boolean,
        var subsystem: List<Subsystem>
): Updateable<Unit> {
    override fun update(context: Unit) {
        subsystem.forEach { it.update(this) }
    }
}