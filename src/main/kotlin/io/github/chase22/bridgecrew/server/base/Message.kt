package io.github.chase22.bridgecrew.server.base

import io.github.chase22.bridgecrew.server.ship.Ship
import java.time.ZoneId
import java.time.ZonedDateTime


open class Message<T>(
        val messageType: MessageType,
        val payload: T
) {
    val timestamp: ZonedDateTime = ZonedDateTime.now(ZoneId.of("UTC"))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Message<*>

        if (messageType != other.messageType) return false
        if (payload != other.payload) return false

        return true
    }

    override fun hashCode(): Int {
        var result = messageType.hashCode()
        result = 31 * result + (payload?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Message(messageType=$messageType, payload=$payload, timestamp=$timestamp)"
    }

}

enum class MessageType {
    TEXT,
    SHIP
}

class TextMessage(from: String, message: String) : Message<TextMessagePayload>(
        MessageType.TEXT,
        TextMessagePayload(from, message)
) {
    constructor(message: String): this("SYSTEM", message)
}

data class TextMessagePayload(val from: String, val message: String)

class ShipMessage(ship: Ship) : Message<Ship>(MessageType.SHIP, ship)