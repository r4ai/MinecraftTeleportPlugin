package com.github.r4ai

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object EventListener: Listener {
    @EventHandler
    fun on(e: PlayerJoinEvent) {
        val p = e.player
        e.joinMessage = "お帰りなさいませ ${p.name}様"
    }
}