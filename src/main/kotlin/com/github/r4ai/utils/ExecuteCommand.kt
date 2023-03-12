package com.github.r4ai.utils

import com.github.r4ai.commands.CustomCommand
import org.bukkit.entity.Player

object ExecuteCommand {
    fun executeCommand(player: Player, commands: List<CustomCommand>, args: List<String> = emptyList()) =
        player.performCommand(getCommand(commands, args))

    fun getCommand(commands: List<CustomCommand>, args: List<String> = emptyList()) =
        "tpplugin exec ${commands.joinToString(" ") { it.label }} ${args.joinToString(" ")}"
}
