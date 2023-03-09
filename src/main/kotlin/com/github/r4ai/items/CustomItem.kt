package com.github.r4ai.items

import com.github.r4ai.Main
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.persistence.PersistentDataType

/*
 * This class is a template for creating custom items.
 * To create a custom item, create a new class that extends this class.
 *
 * ## Constructor parameters
 *
 * @param name: The name of the item. Must be [a-z0-9/._-] and unique.
 * @param itemLore: The lore of the item.
 * @param material: The material of the item.
 * @param recipeShape: The shape of the recipe.
 * @param recipeIngredients: The ingredients of the recipe.
 * @param itemIsUnbreakable: Whether the item is unbreakable.
 *                           If null, the item will be unbreakable if it was unbreakable before.
 *
 * ## Utility methods
 *
 * @method getItem(): Returns the item.
 * @method getRecipe(): Returns the recipe.
 * @method isMatch(item: ItemStack): Returns whether the given `item` is equal to this `CustomItem`.
 *                                   This method is used to check if the item is the same as this `CustomItem`.
 */
open class CustomItem(
    private val name: String, // Name must be [a-z0-9/._-] and unique
    private val itemLore: List<String>?,
    private val material: Material,
    private val recipeShape: List<String>,
    private val recipeIngredients: Map<Char, Material>,
    private val itemIsUnbreakable: Boolean? = null,
) : Listener {
    val itemId = name.lowercase().replace(' ', '_')
    val itemNamespacedKey = NamespacedKey(Main.plugin, itemId)

    fun getItem(): ItemStack {
        return ItemStack(material).apply {
            itemMeta = itemMeta?.apply {
                setDisplayName(name)
                lore = itemLore
                isUnbreakable = itemIsUnbreakable ?: isUnbreakable
                persistentDataContainer.set(itemNamespacedKey, PersistentDataType.BYTE, 1)
            }
        }
    }

    fun getRecipe(): Recipe {
        return ShapedRecipe(itemNamespacedKey, getItem()).apply {
            shape(*recipeShape.toTypedArray())
            recipeIngredients.forEach { (key, material) ->
                setIngredient(key, material)
            }
        }
    }

    fun isMatch(item: ItemStack): Boolean {
        return item.type == material && item.itemMeta?.persistentDataContainer?.has(
            itemNamespacedKey,
            PersistentDataType.BYTE
        ) == true
    }
}
