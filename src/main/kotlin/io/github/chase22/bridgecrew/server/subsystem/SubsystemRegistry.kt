package io.github.chase22.bridgecrew.server.subsystem

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.stream.Collectors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubsystemRegistry @Inject constructor(subsystems: List<Subsystem>) {
    private val subsystemMap: Map<String, Subsystem> =
            subsystems.stream().collect(Collectors.toMap(Subsystem::id) { it })

    init {
        subsystemMap.values.joinToString(",", "[", "]") { it.id }.let {
            LOGGER.info("Initialized Subsystem registry with Ids: $it")
        }
    }

    fun getById(id: String): Subsystem? {
        return subsystemMap[id]
    }

    fun getByType(type: SubsystemType): List<Subsystem> {
        return subsystemMap.values.filter { it.getTypes().contains(type) }
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(SubsystemRegistry::class.java)
    }
}