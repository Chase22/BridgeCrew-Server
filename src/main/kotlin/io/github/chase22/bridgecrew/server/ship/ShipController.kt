package io.github.chase22.bridgecrew.server.ship

import io.github.chase22.bridgecrew.server.websocket.WebSocketServer
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import javax.inject.Inject

@Controller("/ship")
class ShipController @Inject constructor(
        private val webSocketServer: WebSocketServer
) {
    private var ship: Ship = Ship(
            100, 150, 300, 100, 100, true, emptyList()
    )

    @Get
    fun getShip(): MutableHttpResponse<Ship> = HttpResponse.ok(ship)

    @Post
    fun postShip(@Body ship: Ship): HttpStatus {
        this.ship = ship
        webSocketServer.broadcast(ship)
        return HttpStatus.CREATED
    }
}