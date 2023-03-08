package com.github.r4ai.items

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent

object HelloSword : CustomItem(
    name = "Hello Sword",
    itemLore = listOf("This is a sword that says hello."),
    material = Material.DIAMOND_SWORD,
    recipeShape = listOf(
        " D ",
        "DDD",
        " S "
    ),
    recipeIngredients = mapOf(
        'D' to Material.DIAMOND,
        'S' to Material.STICK
    ),
    itemIsUnbreakable = true
) {
    @EventHandler
    fun onRightClick(e: PlayerInteractEvent) {
        val p = e.player
        val item = e.item ?: return
        if (this.isMatch(item) && e.action.name.contains("RIGHT")) {
            e.isCancelled = true
            p.sendMessage("Hello, ${p.name}!")
        }
    }
}