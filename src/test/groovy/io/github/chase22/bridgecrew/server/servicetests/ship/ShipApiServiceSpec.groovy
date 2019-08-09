package io.github.chase22.bridgecrew.server.servicetests.ship

import io.github.chase22.bridgecrew.server.infrastructure.traits.WithShipApiActor
import io.github.chase22.bridgecrew.server.ship.ShipRvo
import io.micronaut.test.annotation.MicronautTest
import spock.lang.PendingFeature
import spock.lang.Specification

@MicronautTest
class ShipApiServiceSpec extends Specification implements WithShipApiActor {

    @PendingFeature
    def "/ship should return the ship with the given id"() {
        given:
        String id = "someUUid"

        when:
        ShipRvo ship = shipApiActor.getShip(id)

        then:
        ship.id == id
    }
}