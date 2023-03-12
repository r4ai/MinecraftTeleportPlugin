package com.github.r4ai.utils

import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable

// TODO: fix the bug that the cool down time does not decrease
class CoolDownTask(
    private val plugin: Plugin,
    private val player: Player,
    private val item: ItemStack,
    private val coolDownTime: Int = 0,
    private val coolDownKey: String = "coolDown",
    private val coolDownMessage: String = "${ColorCode.AQUA}クールダウン中"
) :
    BukkitRunnable() {
    override fun run() {
        val coolDownKey = NamespacedKey(plugin, coolDownKey)
        val coolDown = item.itemMeta?.persistentDataContainer?.get(
            coolDownKey, PersistentDataType.INTEGER
        ) ?: coolDownTime
        if (coolDown > 0) {
            item.itemMeta?.persistentDataContainer?.set(
                coolDownKey, PersistentDataType.INTEGER, coolDown - 1
            )
            player.sendMessage("$coolDownMessage: $coolDown")
        } else {
            cancel()
        }
    }

    fun start() {
        runTaskTimer(plugin, 0L, 20L)
    }
}