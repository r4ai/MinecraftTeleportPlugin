package com.github.r4ai.items

import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.util.Vector

// TODO: Cool down for teleporting
object MagicMoveWand : CustomItem(
    name = "Magic Move Wand",
    itemLore = listOf("This is a wand that moves you."),
    material = Material.STICK,
    recipeShape = listOf(
        " D ",
        " G ",
        " I "
    ),
    recipeIngredients = mapOf(
        'D' to Material.DIAMOND,
        'G' to Material.GOLD_INGOT,
        'I' to Material.IRON_INGOT
    ),
    itemIsUnbreakable = true
) {
    // Jump forward 6 blocks
    @EventHandler
    fun onRightClick(e: PlayerInteractEvent) {
        val p = e.player
        val item = e.item ?: return
        if (this.isMatch(item) && e.action.name.contains("RIGHT")) {
            e.isCancelled = true

            // orientation is a vector that points to the direction the player is facing.
            // x: -1 is left, 1 is right
            // y: -1 is down, 1 is up
            // z: -1 is back, 1 is front
            // (-1, 1)   ↑ z  (1, 1)
            //           |
            // ←---------y--------→ x
            //           |
            // (-1, -1)  ↓ z  (1, -1)
            val orientation = p.location.direction

            val velocity =
                Vector(orientation.x, 0.1, orientation.z).normalize().multiply(1.5)
            val location = p.location
            p.world.spawnParticle(Particle.EXPLOSION_LARGE, location, 1)
            p.world.playSound(location, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 0.6f, 1.2f)
            p.velocity = velocity
        }
    }
}