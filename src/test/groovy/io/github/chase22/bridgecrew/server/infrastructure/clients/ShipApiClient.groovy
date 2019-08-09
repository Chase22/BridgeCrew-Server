package io.github.chase22.bridgecrew.server.infrastructure.clients


import io.github.chase22.bridgecrew.server.ship.ShipOperations
import io.micronaut.http.client.annotation.Client

@Client("/ship")
interface ShipApiClient extends ShipOperations {

}