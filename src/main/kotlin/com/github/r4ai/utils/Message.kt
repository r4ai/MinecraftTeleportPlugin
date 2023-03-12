package com.github.r4ai.utils

import org.bukkit.entity.Player

object Message {
    fun text(color: ColorCode, text: String) = "${color.code}$text"

    fun sendMessage(player: Player, color: ColorCode, text: String) =
        player.sendMessage(text(color, text))
}