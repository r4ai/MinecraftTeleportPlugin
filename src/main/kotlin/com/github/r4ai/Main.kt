package com.github.r4ai

import org.bukkit.command.CommandExecutor
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        logger.info("Hello, World!")
        server.pluginManager.registerEvents(EventListener, this)
        registerCommand("hello", HelloCommand)
        registerCommand("teleport_near_player", TeleportCommand)
    }

    private fun registerCommand(label: String, executor: CommandExecutor) {
        getCommand(label)?.run {
            this.setExecutor(executor)
            logger.info("registered /$label")
        } ?: logger.severe("Failed to register /$label")
    }
}
