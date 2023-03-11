package com.github.r4ai.commands

import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object Teleport : CustomCommand(
    label = "teleport",
    aliases = listOf("tp"),
    command = fun(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("このコマンドはプレイヤーのみが実行できます")
            return true
        }
        if (args.size != 3) {
            sender.sendMessage("引数の数が違います")
            return false
        }
        val xyz = args.map {
            it.toDoubleOrNull() ?: run {
                sender.sendMessage("引数が不正です")
                return false
            }
        }
        val location = Location(sender.world, xyz[0], xyz[1], xyz[2])
        sender.teleport(location)
        sender.sendMessage("`${xyz[0]}, ${xyz[1]}, ${xyz[2]}`へテレポートしました")
        return true
    },
    tabCompleter = fun(
        _: CommandSender,
        _: Command,
        _: String,
        args: Array<out String>
    ): MutableList<String>? {
        return when (args.size) {
            1 -> mutableListOf("0")
            2 -> mutableListOf("0")
            3 -> mutableListOf("0")
            in 4..Int.MAX_VALUE -> emptyList<String>().toMutableList()
            else -> null
        }
    }
)
