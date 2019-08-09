package io.github.chase22.bridgecrew.server.infrastructure.actors

import io.github.chase22.bridgecrew.server.infrastructure.clients.ShipApiClient
import io.github.chase22.bridgecrew.server.ship.ShipRvo

class ShipApiActor {

    private final ShipApiClient shipApiClient

    ShipApiActor(final ShipApiClient shipApiClient) {
        this.shipApiClient = shipApiClient
    }

    ShipRvo getShip(String id) {
        return shipApiClient.getShip(id).blockingGet().getBody(ShipRvo).get()
    }

}
