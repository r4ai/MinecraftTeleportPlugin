package com.github.r4ai

import com.github.r4ai.commands.HelloCommand
import com.github.r4ai.commands.TeleportCommand
import com.github.r4ai.commands.TeleportPluginCommand
import com.github.r4ai.items.HelloSword
import org.bukkit.command.CommandExecutor
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        logger.info("Hello, World!")
        server.pluginManager.registerEvents(EventListener, this)
        registerCommand("hello", HelloCommand)
        registerCommand("teleport_near_player", TeleportCommand)
        registerCommand("teleport_plugin", TeleportPluginCommand)
        registerItem(HelloSword.item(), HelloSword.recipe())
    }

    private fun registerCommand(label: String, executor: CommandExecutor) {
        getCommand(label)?.run {
            this.setExecutor(executor)
            logger.info("registered command /$label")
        } ?: logger.severe("Failed to register command /$label")
    }

    private fun registerItem(item: ItemStack, recipe: Recipe) {
        server.addRecipe(recipe)
        logger.info("registered item ${item.type.name}")
    }
}
