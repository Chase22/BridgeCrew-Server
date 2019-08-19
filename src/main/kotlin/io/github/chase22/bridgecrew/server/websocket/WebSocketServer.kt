package io.github.chase22.bridgecrew.server.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.chase22.bridgecrew.server.base.Message
import io.github.chase22.bridgecrew.server.base.TextMessage
import io.github.chase22.bridgecrew.server.game.Game
import io.github.chase22.bridgecrew.server.game.GameService
import io.micronaut.websocket.WebSocketSession
import io.micronaut.websocket.annotation.OnClose
import io.micronaut.websocket.annotation.OnMessage
import io.micronaut.websocket.annotation.OnOpen
import io.micronaut.websocket.annotation.ServerWebSocket
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Inject

@ServerWebSocket("/stream/game/{id}/{nickname}")
class WebSocketServer @Inject constructor(
        private val objectMapper: ObjectMapper,
        private val gameService: GameService
) {

    @OnOpen
    fun onOpen(id: String, nickname: String, session: WebSocketSession) {
        LOGGER.info("New Connection")
        getGame(id, session)?.connect(nickname, session, this)
    }

    @OnClose
    fun onClose(id: String, nickname: String, session: WebSocketSession) {
        LOGGER.info("Connection lost")

        getGame(id, session)?.disconnect(nickname, session, this)
    }

    @OnMessage
    fun onMessage(id: String, nickname: String, message: String, session: WebSocketSession) {
        LOGGER.info("Received message: {}", message)
        getGame(id, session)?.onMessage(nickname, message, session, this)
    }

    private fun getGame(id: String, source: WebSocketSession): Game? {
        val game = gameService.games[id]

        if (game == null) {
            send(TextMessage("No game found with Id $id"), source)
        }

        return game
    }

    fun broadcast(message: Message<*>, source: WebSocketSession, sessions: Collection<WebSocketSession>) {
        val messageString: String = objectMapper.writeValueAsString(message)
        LOGGER.info("Send Message {}", message)
        sessions.filterNot { it.id == source.id }.forEach { it.sendSync(messageString) }
    }

    private fun send(message: Message<*>, receiver: WebSocketSession) {
        val messageString: String = objectMapper.writeValueAsString(message)
        LOGGER.info("Send Message {}", message)
        receiver.sendSync(messageString)
    }

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(WebSocketServer::class.java)
    }
}