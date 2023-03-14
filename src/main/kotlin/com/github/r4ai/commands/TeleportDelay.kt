package com.github.r4ai.commands

import com.github.r4ai.Main.Companion.plugin
import com.github.r4ai.utils.Message
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

/*
 * Usage:
 *   /exec teleport_delay <delay> <x> <y> <z>
 * Args:
 *   <delay>: Delay in seconds. [long]
 *   <x>:     X coordinate      [double]
 *   <y>:     Y coordinate      [double]
 *   <z>:     Z coordinate      [double]
 * Warning:
 *   Only players can execute this command.
 */
object TeleportDelay : CustomCommand(
    label = "teleport_delay",
    aliases = listOf("tp_delay", "tpd"),
    command = fun(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("このコマンドはプレイヤーから実行してください")
            return false
        }
        when (args.size) {
            4 -> {
                val delay = args[0].toDoubleOrNull() ?: return false
                val toTpLocation = getToTpLocation(sender, args.sliceArray(1 until args.size)) ?: return false
                Message.sendActionBar(
                    sender,
                    "Teleporting to ${toTpLocation.x}, ${toTpLocation.y}, ${toTpLocation.z} in $delay seconds..."
                )
                spawnTpParticle(sender)
                object : BukkitRunnable() {
                    override fun run() {
                        sender.teleport(toTpLocation)
                    }
                }.runTaskLater(plugin, (delay * 20L).toLong())
            }

            else -> {
                sender.sendMessage("引数が不正です")
                return false
            }
        }
        return true
    },
    tabCompleter = fun(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String>? {
        return when (args.size) {
            1 -> mutableListOf("<Delay>")
            2 -> mutableListOf("<X>")
            3 -> mutableListOf("<Y>")
            4 -> mutableListOf("<Z>")
            in 5..Int.MAX_VALUE -> emptyList<String>().toMutableList()
            else -> null
        }
    }
)

private fun getToTpLocation(player: Player, args: Array<out String>): Location? {
    if (args.size >= 3) {
        val x = args[0].toDoubleOrNull() ?: return null
        val y = args[1].toDoubleOrNull() ?: return null
        val z = args[2].toDoubleOrNull() ?: return null
        val toTpLocation = player.location.clone().apply {
            this.x = x
            this.y = y
            this.z = z
        }
        return toTpLocation
    } else {
        return null
    }
}


private fun spawnTpParticle(player: Player) {
    player.world.spawnParticle(Particle.SPELL_WITCH, player.location, 100, 0.5, 1.0, 0.5)
}