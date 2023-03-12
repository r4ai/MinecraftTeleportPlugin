package com.github.r4ai.items

import com.github.r4ai.Main.Companion.plugin
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe

object ItemManager {
    // ! CHANGE REQUIRED !
    // when you add new item, add it to this list
    private val objects = listOf(
        HelloSword,
        HomeTeleportFeather,
        MagicMoveWand,
        TeleportBerry
    )

    // ===================================
    // ! BELOW IS NOT REQUIRED TO CHANGE !

    val ids = objects.map { it.itemId }

    fun getObject(id: String): CustomItem? = objects.find { it.itemId == id }

    fun getItem(id: String): ItemStack? = getObject(id)?.getItem()

    fun getRecipe(id: String): Recipe? = getObject(id)?.getRecipe()

    fun init() {
        objects.forEach { registerItem(it) }
    }

    fun tabCompletion(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String>? {
        return when (args.size) {
            1 -> ids.toMutableList()
            in 2..Int.MAX_VALUE -> mutableListOf()
            else -> null
        }
    }

    private fun registerItem(obj: CustomItem?) {
        val item = obj?.getItem()
        val recipe = obj?.getRecipe()
        if (obj != null && item != null && recipe != null) {
            plugin.server.addRecipe(recipe)
            plugin.server.pluginManager.registerEvents(obj, plugin)
            plugin.logger.info("registered item ${obj.itemId}")
        }
    }
}