package com.github.r4ai.items

import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe

object ItemManager {
    val ids = listOf(
        "hello_sword"
    )

    fun getItem(id: String): ItemStack? = when (id) {
        "hello_sword" -> HelloSword.item()
        else -> null
    }

    fun getRecipe(id: String): Recipe? = when (id) {
        "hello_sword" -> HelloSword.recipe()
        else -> null
    }
}