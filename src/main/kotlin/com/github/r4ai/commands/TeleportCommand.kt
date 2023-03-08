package com.github.r4ai.commands

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object TeleportCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        // * Check if the sender is a player
        if (sender !is Player) {
            sender.sendMessage("このコマンドはプレイヤーのみが実行できます")
            return true
        }

        // * Check if there is a player near the sender
        val target = findNearestPlayer(sender) ?: run {
            sender.sendMessage("近くにプレイヤーがいません")
            return true
        }

        // * Get the coordinates from the command arguments
        val xyz = if (args.size == 3) {
            args.map {
                it.toDoubleOrNull() ?: run {
                    sender.sendMessage("引数が不正です")
                    return false
                }
            }
        } else {
            sender.sendMessage("引数の数が違います")
            return false
        }
        val location = Location(target.world, xyz[0], xyz[1], xyz[2])

        // * Teleport the player to the location
        target.teleport(location)
        sender.sendMessage("${target.name}を`${xyz[0]}, ${xyz[1]}, ${xyz[2]}`へテレポートさせました")

        return true
    }

    private fun findNearestPlayer(player: Player): Player? {
        var minDistance = Double.MAX_VALUE
        var nearestPlayer: Player? = null

        for (p in Bukkit.getOnlinePlayers()) {
            if (p == player) continue

            val distance = player.location.distance(p.location)

            if (distance < minDistance) {
                minDistance = distance
                nearestPlayer = p
            }
        }

        return nearestPlayer
    }
}