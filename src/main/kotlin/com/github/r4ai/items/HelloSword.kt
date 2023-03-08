package com.github.r4ai.items

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe

object HelloSword {
    fun item(): ItemStack {
        return ItemStack(Material.DIAMOND_SWORD).apply {
            itemMeta = itemMeta?.apply {
                setDisplayName("Hello, Sword!")
                lore = listOf("This is a sword that says hello.")
                isUnbreakable = true
                addEnchant(Enchantment.DAMAGE_ALL, 5, true)
            }
        }
    }

    fun recipe(): Recipe {
        return ShapedRecipe(
            NamespacedKey.minecraft("hello_sword"),
            item()
        ).apply {
            shape(
                " D ",
                "DDD",
                " S "
            )
            setIngredient('D', Material.DIAMOND)
            setIngredient('S', Material.STICK)
        }
    }
}