package io.github.chase22.bridgecrew.server.infrastructure.traits

import io.github.chase22.bridgecrew.server.infrastructure.actors.ShipApiActor
import org.junit.Before

trait WithShipApiActor extends WithShipApiClient {
    ShipApiActor shipApiActor

    @Before
    def setupShipApiActor() {
        shipApiActor = new ShipApiActor(shipApiClient)
    }
}