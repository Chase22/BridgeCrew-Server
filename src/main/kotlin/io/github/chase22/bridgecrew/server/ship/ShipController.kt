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
        private val webSocketServer: WebSocketServer
) : ShipOperations {

    override fun getSubsystems(id: String, type: SubsystemType?): Single<MutableHttpResponse<List<Subsystem>>> {
        return Single.just(HttpResponse.ok(emptyList()))
    }

    private var ship: Ship = Ship(
            100, 150, 300, 100, 100, true, emptyList()
    )

    @Get
    override fun getShip(@PathVariable id: String): Single<MutableHttpResponse<Ship>> = Single.just(HttpResponse.ok(ship))

    @Post
    override fun postShip(@Body ship: Ship): Single<HttpStatus> {
        this.ship = ship
        webSocketServer.broadcast(ship)
        return Single.just(HttpStatus.CREATED)
    }
}