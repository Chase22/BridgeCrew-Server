package io.github.chase22.bridgecrew.server.base

import io.github.chase22.bridgecrew.server.ship.Ship


open class Message<T>(
        val messageType: MessageType,
        val payload: T
) {
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
}

enum class MessageType {
    TEXT,
    SHIP
}

class TextMessage(message: String): Message<String>(MessageType.TEXT, message)
class ShipMessage(ship: Ship): Message<Ship>(MessageType.SHIP, ship)