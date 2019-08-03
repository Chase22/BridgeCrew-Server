package io.github.chase22.bridgecrew.server.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.websocket.WebSocketSession
import io.micronaut.websocket.annotation.OnClose
import io.micronaut.websocket.annotation.OnMessage
import io.micronaut.websocket.annotation.OnOpen
import io.micronaut.websocket.annotation.ServerWebSocket
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Inject

@ServerWebSocket("/stream/ship")
class WebSocketServer @Inject constructor(
        private val objectMapper: ObjectMapper
) {

    private val sessions: MutableMap<String, WebSocketSession> = HashMap()

    @OnOpen
    fun onOpen(session: WebSocketSession) {
        LOGGER.info("New Connection")
        sessions.put(session.id, session)
    }

    @OnClose
    fun onClose(session: WebSocketSession) {
        LOGGER.info("Connection lost")
        sessions.remove(session.id)
    }

    @OnMessage
    fun onMessage(message: String) {
        LOGGER.info("Received message: {}", message)
    }

    fun broadcast(message: String) {
        LOGGER.info("Send Message {}", message)
        sessions.values.forEach { it.sendSync(message) }
    }

    fun broadcast(message: Any) {
        broadcast(objectMapper.writeValueAsString(message))
    }

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(WebSocketServer::class.java)
    }
}