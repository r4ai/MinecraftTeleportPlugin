package com.github.r4ai

import com.github.r4ai.commands.HelloCommand
import com.github.r4ai.commands.TeleportCommand
import com.github.r4ai.commands.TeleportPluginCommand
import com.github.r4ai.items.ItemManager
import org.bukkit.command.CommandExecutor
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    companion object {
        lateinit var plugin: JavaPlugin
            private set
    }

    override fun onEnable() {
        logger.info("Hello, World!")
        plugin = this
        ItemManager.init()
        registerCommand("hello", HelloCommand)
        registerCommand("teleport_near_player", TeleportCommand)
        registerCommand("teleport_plugin", TeleportPluginCommand)
    }

    private fun registerCommand(label: String, executor: CommandExecutor) {
        getCommand(label)?.run {
            this.setExecutor(executor)
            logger.info("registered command /$label")
        } ?: logger.severe("Failed to register command /$label")
    }
}
