package io.github.chase22.bridgecrew.server.game

import io.github.chase22.bridgecrew.server.base.TextMessage
import io.github.chase22.bridgecrew.server.websocket.WebSocketServer
import io.micronaut.websocket.WebSocketSession
import javax.inject.Singleton

class Game(
        val id: String
        ) {
    private val gameSessions: MutableMap<String, WebSocketSession> = HashMap()

    fun connect(session: WebSocketSession, webSocketServer: WebSocketServer) {
        webSocketServer.broadcast(TextMessage("User joined"), session, gameSessions.values)
    }

    fun disconnect(session: WebSocketSession, webSocketServer: WebSocketServer) {
        webSocketServer.broadcast(TextMessage("User joined"), session, gameSessions.values)
    }

    fun onMessage(message: String, session: WebSocketSession, webSocketServer: WebSocketServer) {
        webSocketServer.broadcast(TextMessage(message), session, gameSessions.values)
    }


}

@Singleton
class GameService {
    val games: MutableMap<String, Game> = HashMap()

    fun addGame(game: Game) = games.put(game.id, game)
    fun removeGame(game: Game) = removeGame(game.id)
    fun removeGame(id: String) = games.remove(id)


}