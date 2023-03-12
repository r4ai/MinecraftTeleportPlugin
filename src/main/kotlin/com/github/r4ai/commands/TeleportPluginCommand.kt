package com.github.r4ai.commands

import com.github.r4ai.items.ItemManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

object TeleportPluginCommand : CommandExecutor, TabCompleter {
    val subCommands = mutableListOf(
        "item",
        "exec"
    )

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage("引数が不正です")
            return false
        }
        return when (args[0]) {
            "item" -> item(sender, command, args[0], args.sliceArray(1 until args.size))
            "exec" -> exec(sender, command, args[0], args.sliceArray(1 until args.size))
            else -> {
                sender.sendMessage("引数が不正です")
                false
            }
        }
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String>? {
        if (args.isEmpty()) return null
        if (args.size == 1) return subCommands
        if (args.size >= 2) {
            return when (args[0]) {
                "item" -> ItemManager.tabCompletion(
                    sender, command, args[0], args.sliceArray(1 until args.size)
                )

                "exec" -> CommandManager.tabCompletion(
                    sender, command, args[0], args.sliceArray(1 until args.size)
                )

                else -> null
            }
        }
        return null
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

    private fun exec(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage("引数の数が違います")
            return false
        }

        when (args[0]) {
            in CommandManager.labels -> {
                val commandObject = CommandManager.getCommand(args[0]) ?: run {
                    sender.sendMessage("存在しないコマンドです")
                    sender.sendMessage("コマンド一覧: ${CommandManager.labels.joinToString(", ")}")
                    return true
                }
                commandObject.onCommand(
                    sender, command, args[0], args.sliceArray(1 until args.size)
                )
            }

            in CommandManager.aliases -> {
                val commandObject = CommandManager.getLabel(args[0])
                    ?.let { CommandManager.getCommand(it) }
                    ?: run {
                        sender.sendMessage("存在しないコマンドです")
                        sender.sendMessage("コマンド一覧: ${CommandManager.labels.joinToString(", ")}")
                        return true
                    }
                commandObject.onCommand(
                    sender, command, args[0], args.sliceArray(1 until args.size)
                )
            }

            else -> {
                sender.sendMessage("存在しないコマンドです")
                sender.sendMessage("コマンド一覧: ${CommandManager.labels.joinToString(", ")}")
                return true
            }
        }
        return true
    }
}
