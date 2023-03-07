package com.github.r4ai

import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        logger.info("Hello, World!")
        server.pluginManager.registerEvents(EventListener, this)
    }
}
