package com.github.r4ai.events

import com.github.r4ai.commands.TeleportDelay
import com.github.r4ai.utils.ExecuteCommand.executeCommand
import com.github.r4ai.utils.Message.sendActionBar
import org.bukkit.Location
import org.bukkit.block.Sign
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

/*
 * Teleports the player to the location specified in the sign.
 * The sign must be in the following format:
 * ```
 *
 * [teleport]
 * x, y, z
 *
 * ```
 */
object SignTeleport : CustomEvent() {
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        if (event.action == Action.RIGHT_CLICK_BLOCK && event.clickedBlock?.type?.name?.contains("SIGN") == true) {
            val sign = event.clickedBlock?.state as? Sign ?: return
            if (sign.getLine(1) == "[teleport]") {
                val coordinates = sign.getLine(2).split(", ")
                val x = coordinates[0].toDoubleOrNull() ?: return
                val y = coordinates[1].toDoubleOrNull() ?: return
                val z = coordinates[2].toDoubleOrNull() ?: return
                val toTpLocation = Location(player.world, x, y, z)
                // TODO: Add config option for max distance
                val maxDistance = 10
                if (player.location.distance(sign.location) > maxDistance) {
                    sendActionBar(player, "You need to be closer to the sign to teleport!")
                } else {
                    // TODO: Add cost for teleportation (priority: high)
                    // TODO: Add config option for teleportation cost (priority: low)
                    // TODO: Add config option for teleportation delay (priority: low)
                    // TODO: Check if toTpLocation is safe (priority: medium)
                    val delay = 1.5
                    executeCommand(
                        player,
                        listOf(TeleportDelay),
                        listOf(delay, toTpLocation.blockX, toTpLocation.blockY, toTpLocation.blockZ)
                            .map { it.toString() }
                    )
                }
                return
            }
        }
    }
}