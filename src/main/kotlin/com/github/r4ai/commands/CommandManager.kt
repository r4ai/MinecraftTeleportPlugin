package com.github.r4ai.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender

object CommandManager {
    // ! CHANGE REQUIRED !
    // when you add new command, add it to this list
    private val objects = listOf(
        Home,
        Teleport
    )
    // ===================================

    // ! BELOW IS NOT REQUIRED TO CHANGE !
    val labels = objects.map { it.label }
    val permissions = objects.map { it.permission }

    fun getCommand(label: String): CustomCommand? = objects.find { it.label == label }

    fun tabCompletion(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String>? {
        return when (args.size) {
            1 -> labels.toMutableList()
            in 2..Int.MAX_VALUE ->
                getCommand(args[0])
                    ?.onTabComplete(sender, command, label, args.sliceArray(1 until args.size))
                    ?: emptyList<String>().toMutableList()

            else -> null
        }
    }
}