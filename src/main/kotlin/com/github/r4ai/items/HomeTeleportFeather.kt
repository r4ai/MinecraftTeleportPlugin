﻿package com.github.r4ai.items

import com.github.r4ai.commands.Home
import com.github.r4ai.utils.ExecuteCommand.executeCommand
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
    fun onRightClick(e: PlayerInteractEvent) {
        val p = e.player
        val item = e.item ?: return
        if (this.isMatch(item) && e.action.name.contains("RIGHT")) {
            e.isCancelled = true
            executeCommand(p, listOf(Home))
        }
    }
}
