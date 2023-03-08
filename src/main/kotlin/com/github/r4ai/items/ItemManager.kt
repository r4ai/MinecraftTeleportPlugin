package com.github.r4ai.items

import com.github.r4ai.Main.Companion.plugin
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe

object ItemManager {
    val ids = listOf(
        "hello_sword",
        "teleport_feather"
    )

    fun getObject(id: String): CustomItem? = when (id) {
        "hello_sword" -> HelloSword
        "teleport_feather" -> TeleportFeather
        else -> null
    }

    fun getItem(id: String): ItemStack? = getObject(id)?.getItem()

    fun getRecipe(id: String): Recipe? = getObject(id)?.getRecipe()

    fun init() {
        ids.forEach { id ->
            registerItem(getObject(id))
        }
    }

    private fun registerItem(obj: CustomItem?) {
        val item = obj?.getItem()
        val recipe = obj?.getRecipe()
        if (obj != null && item != null && recipe != null) {
            plugin.server.addRecipe(recipe)
            plugin.server.pluginManager.registerEvents(obj, plugin)
            plugin.logger.info("registered item ${item.itemMeta?.displayName}")
        }
    }
}