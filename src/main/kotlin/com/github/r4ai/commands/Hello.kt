package com.github.r4ai.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object Hello : CustomCommand(
    label = "hello",
    aliases = listOf("hi"),

    command = fun(sender: CommandSender, _: Command, _: String, _: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("This command can only be executed by players")
            return true
        }
        sender.sendMessage("Hello, ${sender.name}!")
        return true
    },

    tabCompleter = fun(
        _: CommandSender,
        _: Command,
        _: String,
        args: Array<out String>
    ): MutableList<String>? {
        return when (args.size) {
            in 1..Int.MAX_VALUE -> emptyList<String>().toMutableList()
            else -> null
        }
    }
)