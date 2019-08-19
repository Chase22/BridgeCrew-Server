package io.github.chase22.bridgecrew.server.game

import io.github.chase22.bridgecrew.server.base.TextMessage
import io.github.chase22.bridgecrew.server.websocket.WebSocketServer
import io.micronaut.websocket.WebSocketSession
import javax.inject.Singleton

class Game(
        val id: String
        ) {
    private val gameSessions: MutableMap<String, WebSocketSession> = HashMap()

    fun connect(nickname: String, session: WebSocketSession, webSocketServer: WebSocketServer) {
        gameSessions[session.id] = session
        webSocketServer.broadcast(TextMessage("$nickname joined"), session, gameSessions.values)
    }

    fun disconnect(nickname: String,session: WebSocketSession, webSocketServer: WebSocketServer) {
        gameSessions.remove(session.id)
        webSocketServer.broadcast(TextMessage("$nickname left"), session, gameSessions.values)
    }

    fun onMessage(nickname: String,message: String, session: WebSocketSession, webSocketServer: WebSocketServer) {
        webSocketServer.broadcast(TextMessage(nickname, message), session, gameSessions.values)
    }


}

@Singleton
class GameService {
    val games: MutableMap<String, Game> = hashMapOf(Pair("gameid", Game("gameid")))

    fun addGame(game: Game) = games.put(game.id, game)
    fun removeGame(game: Game) = removeGame(game.id)
    private fun removeGame(id: String) = games.remove(id)


}