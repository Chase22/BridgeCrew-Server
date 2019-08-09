package io.github.chase22.bridgecrew.server.ship

import io.github.chase22.bridgecrew.server.subsystem.Subsystem
import io.github.chase22.bridgecrew.server.subsystem.SubsystemType
import io.github.chase22.bridgecrew.server.websocket.WebSocketServer
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import io.reactivex.Single
import javax.inject.Inject

@Controller("/ship")
class ShipController @Inject constructor(
        private val webSocketServer: WebSocketServer,
        private val shipService: ShipService
) : ShipOperations {

    override fun getSubsystems(id: String, type: SubsystemType?): Single<MutableHttpResponse<List<Subsystem>>> =
            shipService.getShip(id)
                    .map { it.subsystem }
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
        shipService.save(Ship(shipRvo))
        webSocketServer.broadcast(shipRvo)
        return Single.just(HttpStatus.CREATED)
    }
}