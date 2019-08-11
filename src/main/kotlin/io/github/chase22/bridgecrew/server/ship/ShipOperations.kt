package io.github.chase22.bridgecrew.server.ship

import io.github.chase22.bridgecrew.server.subsystem.SubsystemRvo
import io.github.chase22.bridgecrew.server.subsystem.SubsystemType
import io.micronaut.http.HttpStatus
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import io.reactivex.Single

interface ShipOperations {

    @Get("/{id}")
    fun getShip(@PathVariable id: String): Single<MutableHttpResponse<ShipRvo>>

    @Get("/{id}/subsystems")
    fun getSubsystems(@PathVariable id: String, @QueryValue type: SubsystemType?): Single<MutableHttpResponse<List<SubsystemRvo>>>

    @Post
    fun postShip(@Body shipRvo: ShipRvo): Single<HttpStatus>


}