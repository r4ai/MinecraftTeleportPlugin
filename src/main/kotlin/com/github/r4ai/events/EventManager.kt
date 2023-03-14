package com.github.r4ai.events

import com.github.r4ai.Main.Companion.plugin

object EventManager {
    // ! CHANGE REQUIRED !
    // When you create a new event, add it to this list.
    private val objects = listOf(
        SignTeleport
    )

    // ===================================
    // ! BELOW IS NOT REQUIRED TO CHANGE !

    fun init() {
        objects.forEach { registerEvent(it) }
    }

    private fun registerEvent(obj: CustomEvent) {
        plugin.server.pluginManager.registerEvents(obj, plugin)
        plugin.logger.info("Registered event ${obj.javaClass.simpleName}")
    }
}