package io.github.chase22.bridgecrew.server.ship

import io.github.chase22.bridgecrew.server.subsystem.SubsystemRvo
import io.github.chase22.bridgecrew.server.subsystem.SubsystemType
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import io.reactivex.Single
import javax.inject.Inject

@Controller("/ship")
class ShipController @Inject constructor(
        private val shipService: ShipService
) : ShipOperations {

    override fun getSubsystems(id: String, type: SubsystemType?): Single<MutableHttpResponse<List<SubsystemRvo>>> =
            shipService.getShip(id)
                    .map { it.subsystem }
                    .map { t -> t.filter { type?.let { type -> it.getTypes().contains(type) } ?: true } }
                    .map { it.map { subsystem -> subsystem.toRvo() } }
                    .map { HttpResponse.ok(it) }
                    .toSingle(HttpResponse.notFound())

    @Get
    override fun getShip(@PathVariable id: String): Single<MutableHttpResponse<ShipRvo>> =
            shipService.getShip(id)
                    .map(Ship::toRvo)
                    .map { HttpResponse.ok(it) }
                    .toSingle(HttpResponse.notFound())

    @Post
    override fun postShip(@Body shipRvo: ShipRvo): Single<HttpStatus> {
        return shipService.save(shipService.fromRvo(shipRvo)).map { HttpStatus.CREATED }
    }
}