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
    val command: (sender: CommandSender, command: Command, label: String, args: Array<out String>) -> Boolean,
    val tabCompleter: (sender: CommandSender, command: Command, label: String, args: Array<out String>) -> List<String>?
) : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (permission != null && !sender.hasPermission(permission)) {
            sender.sendMessage("権限がありません")
            return false
        }
        return command(sender, command, label, args)
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String>? {
        return tabCompleter(sender, command, label, args)?.toMutableList()
    }
}
