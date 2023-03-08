package com.github.r4ai.items

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent

object HomeTeleportFeather : CustomItem(
    name = "Teleport Feather to Home",
    itemLore = listOf("Teleports you to the spawn point."),
    material = Material.FEATHER,
    recipeShape = listOf(
        " F ",
        " D ",
        " F "
    ),
    recipeIngredients = mapOf(
        'F' to Material.FEATHER,
        'D' to Material.DIAMOND
    ),
    itemIsUnbreakable = true
) {
    @EventHandler
    fun onLeftClick(e: PlayerInteractEvent) {
        val p = e.player
        val item = e.item ?: return
        if (this.isMatch(item) && e.action.name.contains("LEFT")) {
            e.isCancelled = true
            val location = p.bedSpawnLocation ?: p.world.spawnLocation
            p.teleport(location)
        }
    }
}