package io.github.chase22.bridgecrew.server.infrastructure.actors

import io.github.chase22.bridgecrew.server.infrastructure.clients.ShipApiClient
import io.github.chase22.bridgecrew.server.ship.ShipRvo
import io.github.chase22.bridgecrew.server.subsystem.SubsystemRvo
import io.github.chase22.bridgecrew.server.subsystem.SubsystemType

import static io.micronaut.core.type.Argument.listOf

class ShipApiActor {

    private final ShipApiClient shipApiClient

    ShipApiActor(final ShipApiClient shipApiClient) {
        this.shipApiClient = shipApiClient
    }

    ShipRvo getShip(String id) {
        return shipApiClient.getShip(id).blockingGet().getBody(ShipRvo).get()
    }

    List<SubsystemRvo> getSubsystems(String shipId, String type = null) {
        return shipApiClient.getSubsystems(shipId, type != null ? SubsystemType.valueOf(type) : null)
                .blockingGet().getBody(listOf(SubsystemRvo)).get()
    }

}
