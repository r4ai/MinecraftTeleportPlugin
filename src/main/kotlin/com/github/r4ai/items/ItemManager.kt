package com.github.r4ai.items

import com.github.r4ai.Main.Companion.plugin
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe

object ItemManager {
    // ! CHANGE REQUIRED !
    // when you add new item, add it to this list
    private val objects = listOf(
        HelloSword,
        HomeTeleportFeather,
        MagicMoveWand
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