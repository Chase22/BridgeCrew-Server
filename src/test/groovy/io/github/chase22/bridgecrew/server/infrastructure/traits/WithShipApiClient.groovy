package io.github.chase22.bridgecrew.server.infrastructure.traits

import io.github.chase22.bridgecrew.server.infrastructure.clients.ShipApiClient

import javax.inject.Inject

trait WithShipApiClient {

    @Inject
    ShipApiClient shipApiClient
}