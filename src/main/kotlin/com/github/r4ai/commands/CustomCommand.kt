package com.github.r4ai.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

/*
 * CustomCommand is a class that can be used to create a command.
 *
 * ## Constructor parameters
 *
 * @param label: String
 * @param permission: String?
 *                    If null, everyone can use this command.
 * @param command: A function that is called onCommand.
 *                 Automatically checks permission.
 */
open class CustomCommand(
    val label: String,
    val aliases: List<String> = emptyList(),
    val permission: String? = null,
    val subCommands: List<CustomCommand> = emptyList(),
    val command: (sender: CommandSender, command: Command, label: String, args: Array<out String>) -> Boolean,
    val tabCompleter: (sender: CommandSender, command: Command, label: String, args: Array<out String>) -> List<String>?
) : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (permission != null && !sender.hasPermission(permission)) {
            sender.sendMessage("権限がありません")
            return false
        }
        return if (args.isEmpty()) {
            command(sender, command, label, args)
        } else {
            val subCommand = subCommands.find {
                it.label == args[0] || it.aliases.contains(args[0])
            }
            subCommand
                ?.onCommand(sender, command, label, args.sliceArray(1 until args.size))
                ?: command(sender, command, label, args)
        }
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String>? {
        // sender.sendMessage("Tab complete: label=$label, args=${args.joinToString(", ")}")
        return when (args.size) {
            0 -> tabCompleter(sender, command, label, args)?.toMutableList()
            in 1..Int.MAX_VALUE -> {
                val subCommand = subCommands.find {
                    it.label == args[0] || it.aliases.contains(args[0])
                }
                if (subCommand != null) {
                    subCommand.onTabComplete(sender, command, label, args.sliceArray(1 until args.size))
                } else {
                    tabCompleter(sender, command, label, args)?.toMutableList()
                }
            }

            else -> null
        }
    }
}
