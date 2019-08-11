package io.github.chase22.bridgecrew.server.classtests.ship

import io.github.chase22.bridgecrew.server.ship.Ship
import io.github.chase22.bridgecrew.server.subsystem.Subsystem
import kotlin.Unit
import spock.lang.Specification
import spock.lang.Subject

import static io.github.chase22.bridgecrew.server.subsystem.SubsystemType.*

class ShipClassSpec extends Specification {

    @Subject
    Ship ship = new Ship(100, 100, 500, 100, 100, true, [])

    def "calling update on a Ship should call update on all subsystems"() {
        given:
        Subsystem subsystem1 = Mock() {
            getTypes() >> [OTHER]
        }
        Subsystem subsystem2 = Mock() {
            getTypes() >> [OTHER]
        }
        Subsystem subsystem3 = Mock() {
            getTypes() >> [OTHER]
        }
        ship.subsystem = [subsystem1, subsystem2, subsystem3]

        when:
        ship.update(new Unit() as Unit)

        then:
        1*subsystem1.update(ship)
        1*subsystem2.update(ship)
        1*subsystem3.update(ship)

    }

    def "calling update on a ship should change the temperature according to all subsystems"() {
        given:
        Subsystem subsystem1 = Mock() {
            getTemperatureChange() >> 2
            getTypes() >> [OTHER]
        }
        Subsystem subsystem2 = Mock() {
            getTemperatureChange() >> 5
            getTypes() >> [OTHER]
        }
        ship.subsystem = [subsystem1, subsystem2]

        when:
        ship.update(new Unit() as Unit)

        then:
        ship.temperature == old(ship.temperature) + 7
    }

    def "calling update should call subsystems ordered by type"() {
        given:
        Subsystem subsystem1 = Mock() {
            getTypes() >> [OTHER]
        }
        Subsystem subsystem2 = Mock() {
            getTypes() >> [ENERGY_PRODUCTION]
        }
        Subsystem subsystem3 = Mock() {
            getTypes() >> [TEMPERATURE_MANAGEMENT]
        }
        ship.subsystem = [subsystem1, subsystem2, subsystem3]

        when:
        ship.update(new Unit() as Unit)

        then:
        1 * subsystem2.update(ship)

        then:
        1 * subsystem1.update(ship)

        then:
        1 * subsystem3.update(ship)
    }
}
