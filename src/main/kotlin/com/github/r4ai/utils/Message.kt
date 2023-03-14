package com.github.r4ai.utils

import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.entity.Player

object Message {
    fun colorText(color: ColorCode, text: String) = "${color.code}$text"

    fun sendColorMessage(player: Player, color: ColorCode, text: String) =
        player.sendMessage(colorText(color, text))

    fun sendActionBar(player: Player, text: String, color: ColorCode? = null) =
        if (color == null) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(text))
        } else {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(colorText(color, text)))
        }
}