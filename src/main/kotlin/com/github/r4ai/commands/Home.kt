package com.github.r4ai.commands

import com.github.r4ai.Main.Companion.plugin
import org.bukkit.Particle
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

object Home : CustomCommand(
    label = "home",
    permission = "r4ai.commands.home",
    command = fun(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        // Check if the sender is a player and if arguments are valid
        if (sender !is Player) {
            sender.sendMessage("このコマンドはプレイヤーから実行してください")
            return false
        }
        if (args.isNotEmpty()) {
            sender.sendMessage("引数が不正です")
            return false
        }

        // Generate particles
        sender.world.spawnParticle(Particle.SPELL_WITCH, sender.location, 20, 0.5, 1.0, 0.5)
        sender.sendMessage("Teleporting to home...")

        // Teleport after 1 second
        object : BukkitRunnable() {
            override fun run() {
                val home = sender.bedSpawnLocation ?: sender.world.spawnLocation
                sender.teleport(home)
            }
        }.runTaskLater(plugin, 20L)
        return true
    },
    tabCompleter = fun(_: CommandSender, _: Command, _: String, _: Array<out String>): MutableList<String>? {
        return emptyList<String>().toMutableList()
    }
)
