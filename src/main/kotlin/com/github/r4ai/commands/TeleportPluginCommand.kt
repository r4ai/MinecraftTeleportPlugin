package com.github.r4ai.commands

import com.github.r4ai.items.ItemManager
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

object TeleportPluginCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage("引数が不正です")
            return false
        }
        return when (args[0]) {
            "item" -> item(sender, command, args[0], args.sliceArray(1 until args.size))
            "teleport" -> teleport(sender, command, args[0], args.sliceArray(1 until args.size))
            else -> {
                sender.sendMessage("引数が不正です")
                false
            }
        }
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String>? {
        return when (args.size) {
            1 -> listOf("item", "teleport").toMutableList()

            2 -> when (args[0]) {
                "item" -> ItemManager.ids.toMutableList()
                "teleport" -> listOf("0").toMutableList()
                else -> null
            }

            3 -> when (args[0]) {
                "item" -> emptyList<String>().toMutableList()
                "teleport" -> listOf("0").toMutableList()
                else -> null
            }

            4 -> when (args[0]) {
                "item" -> emptyList<String>().toMutableList()
                "teleport" -> listOf("0").toMutableList()
                else -> null
            }

            in 5..Int.MAX_VALUE -> emptyList<String>().toMutableList()

            else -> null
        }
    }

    private fun item(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("このコマンドはプレイヤーのみが実行できます")
            return true
        }
        if (args.size != 1) {
            sender.sendMessage("引数の数が違います")
            return false
        }
        val item = when (args[0]) {
            in ItemManager.ids -> ItemManager.getItem(args[0]) ?: run {
                sender.sendMessage("存在しないアイテムです")
                sender.sendMessage("アイテム一覧: ${ItemManager.ids.joinToString(", ")}")
                return true
            }

            else -> {
                sender.sendMessage("存在しないアイテムです")
                sender.sendMessage("アイテム一覧: ${ItemManager.ids.joinToString(", ")}")
                return true
            }
        }
        sender.inventory.addItem(item)
        sender.sendMessage("${item.itemMeta?.displayName}を手に入れました")
        return true
    }

    private fun teleport(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
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
    }
}