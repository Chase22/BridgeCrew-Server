package io.github.chase22.bridgecrew.server.servicetests.ship

import io.github.chase22.bridgecrew.server.infrastructure.traits.WithShipApiActor
import io.github.chase22.bridgecrew.server.ship.InMemoryShipRepository
import io.github.chase22.bridgecrew.server.ship.ShipEntity
import io.github.chase22.bridgecrew.server.ship.ShipRepository
import io.github.chase22.bridgecrew.server.ship.ShipRvo
import io.github.chase22.bridgecrew.server.subsystem.SubsystemRvo
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import io.reactivex.Maybe
import spock.lang.Specification

@MicronautTest
class ShipApiServiceSpec extends Specification implements WithShipApiActor {

    def "/ship should return the ship with the given id"() {
        given:
        String id = "someId"

        when:
        ShipRvo ship = shipApiActor.getShip(id)

        then:
        ship.id == id
    }

    def "/ship/subsystems should return all subsystems of a ship"() {
        given:
        String id = "someId"

        when:
        List<SubsystemRvo> subsystems = shipApiActor.getSubsystems(id)

        then:
        subsystems.size() == 3
    }

    def "/ship/subsystems/{type} should return all subsystems of the given type"() {
        given:
        String id = "someId"

        when:
        List<SubsystemRvo> subsystems = shipApiActor.getSubsystems(id, "ENERGY_STORAGE")

        then:
        subsystems.size() == 1
        subsystems.first().types.contains("ENERGY_STORAGE")
    }

    @MockBean(InMemoryShipRepository)
    ShipRepository shipRepositoryMock() {
        return Mock(ShipRepository) {
            findById(_ as String) >> Maybe.just(
                    new ShipEntity(
                            "someId", 100, 100, 100, 100, 100,
                            ["solar", "radiator", "battery"]
                    )
            )
        }
    }
}