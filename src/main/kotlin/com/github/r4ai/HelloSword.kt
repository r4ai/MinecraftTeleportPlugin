package com.github.r4ai

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

object HelloSword {
    fun item(): ItemStack {
        val item = ItemStack(Material.DIAMOND_SWORD)

        item.itemMeta?.apply {
            setDisplayName("Hello, Sword!")
            lore = listOf("This is a sword that says hello.")
            isUnbreakable = true
            addEnchant(Enchantment.DAMAGE_ALL, 5, true)
        }

        return item
    }
}